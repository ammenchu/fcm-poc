package com.copart.framework.fcm.client;

import static com.copart.framework.fcm.message.util.Constants.DOMAIN;
import static com.copart.framework.fcm.message.util.Constants.FCM_ELEMENT;
import static com.copart.framework.fcm.message.util.Constants.FCM_NAMESPACE;
import static com.copart.framework.fcm.message.util.Constants.HOST;
import static org.jivesoftware.smack.filter.StanzaTypeFilter.MESSAGE;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ReconnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.copart.framework.fcm.callback.LoggedConnectionListener;
import com.copart.framework.fcm.extension.FcmExtensionProvider;
import com.copart.framework.fcm.extension.FcmPacketExtension;
import com.copart.framework.fcm.message.out.DownstreamMessageRequest;
import com.copart.framework.fcm.message.util.BackOffStrategy;
import com.copart.framework.fcm.message.util.Constants;
import com.copart.framework.fcm.message.util.MessageUtils;

@Component
public final class FcmCcsClient implements ReconnectionListener, PingFailedListener {

	public static final Logger logger = Logger.getLogger(FcmCcsClient.class.getName());

	private static FcmCcsClient instance = null;
	private XMPPTCPConnection connection;
	private XMPPTCPConnectionConfiguration config;
	private String serverKey = null;
	private String senderId = null;
	private boolean debuggable = false;
	private String username = null;

	@Autowired
	private StanzaListener fcmStanzaListener;

	@Autowired
	private LoggedConnectionListener loggedConnectionListener;

	public void setFcmStanzaListener(StanzaListener fcmStanzaListener) {
		this.fcmStanzaListener = fcmStanzaListener;
	}

	public void setLoggedConnectionListener(LoggedConnectionListener loggedConnectionListener) {
		this.loggedConnectionListener = loggedConnectionListener;
	}

	private final ExecutorService executorService = Executors.newCachedThreadPool();

	public static FcmCcsClient getInstance() {
		if (instance == null) {
			throw new IllegalStateException("You have to prepare the client first");
		}
		return instance;
	}

	public static FcmCcsClient createClient(String senderId, String apiKey, boolean debuggable) {
		synchronized (FcmCcsClient.class) {
			if (instance == null) {
				instance = new FcmCcsClient(senderId, apiKey, debuggable);
			}
		}
		return instance;
	}

	private FcmCcsClient(String senderId, String serverKey, boolean debuggable) {
		this();
		this.serverKey = serverKey;
		this.senderId = senderId;
		this.debuggable = debuggable;
		this.username = this.senderId + "@" + Constants.FCM_SERVER_CONNECTION_ENDPOINT;
	}

	public FcmCcsClient() {
		ProviderManager.addExtensionProvider(FCM_ELEMENT, FCM_NAMESPACE, new FcmExtensionProvider());
	}

	/**
	 * Connects to FCM Cloud Connection Server using the supplied credentials
	 * 
	 */

	@SuppressWarnings("deprecation")
	public void connect() {
		try {
			config = XMPPTCPConnectionConfiguration.builder().setPort(Constants.FCM_PORT)
					// .setHostAddress(InetAddress.getByName(HOST_ADDRESS))
					.setHost(HOST) // --DO NOT UNCOMMENT
					.setXmppDomain(DOMAIN)
					.setSecurityMode(/* Default; Explicit setting for emphasis */SecurityMode.ifpossible)
					.setSendPresence(false).setUsernameAndPassword(username, serverKey)
					.setSocketFactory(SSLSocketFactory.getDefault()).setDebuggerEnabled(debuggable).build();

			connection = new XMPPTCPConnection(config);

			connection.addConnectionListener(loggedConnectionListener);

			// Handle incoming packets (the class implements the PacketListener)
			connection.addAsyncStanzaListener(fcmStanzaListener, MESSAGE);

			// Log all outgoing packets

			connection.addPacketInterceptor(new StanzaListener() {
				@Override
				public void processStanza(final Stanza packet) {
					logger.log(Level.INFO, "Sent: {0}", packet.toXML());
				}
			}, MESSAGE);

			// Configuring Automatic reconnection
			ReconnectionManager manager = ReconnectionManager.getInstanceFor(connection);
			manager.setReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.RANDOM_INCREASING_DELAY);
			manager.enableAutomaticReconnection();
			ReconnectionManager.getInstanceFor(connection).addReconnectionListener(this);

			// Disable Roster at login (in XMPP the contact list is called a "roster")
			Roster.getInstanceFor(connection).setRosterLoadedAtLogin(false);

			// Set the ping interval
			final PingManager pingManager = PingManager.getInstanceFor(connection);
			pingManager.setPingInterval(100);
			pingManager.registerPingFailedListener(this);

			// Connect now then login
			connection.connect();
			connection.login(username, serverKey);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public void send(String jsonRequest) {
		Stanza request = new FcmPacketExtension(jsonRequest).toMessage();
		final BackOffStrategy backoff = new BackOffStrategy();
		while (backoff.shouldRetry()) {
			try {
				connection.sendStanza(request);
				backoff.doNotRetry();
			} catch (Exception e) {
				try {
					backoff.errorOccured2();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Sends a outgoing message to FCM
	 */
	// public void send(DownstreamMessageRequest outMessage) {
	// send(outMessage.toString());
	// }

	/**
	 * Sends a outgoing message to FCM in a background thread so that calling thread
	 * is not blocked, waiting for the response
	 */
	public void sendAsync(DownstreamMessageRequest outMessage) {
		String msgToBeSent = outMessage.toString();
		logger.info("Message to be sent: \n" + MessageUtils.getPrettyPrintedJson(msgToBeSent));
		// Every message will have its own sending task
		executorService.submit(getStanzaTask(msgToBeSent));
	}

	/**
	 * Sends a outgoing message to FCM in a background thread so that calling thread
	 * is not blocked, waiting for the response
	 */
	public void sendAsync(String message) {
		// Every message will have its own sending task
		executorService.submit(getStanzaTask(message));
	}

	private FcmStanzaTask getStanzaTask(String message) {
		// Create the Stanza sending task.
		return new FcmStanzaTask(instance, message);
	}

	/**
	 * Sends a message to multiple recipients (list). Kind of like the old HTTP
	 * message with the list of regIds in the "registration_ids" field.
	 */
	public void sendBroadcast(DownstreamMessageRequest outMessage, List<String> recipients) {
		for (String toRegId : recipients) {
			outMessage.setTo(toRegId);
			send(outMessage.toString());
		}
	}

	@Override
	public void reconnectingIn(int seconds) {
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reconnectionFailed(Exception e) {

	}

	@Override
	public void pingFailed() {
		logger.info("The ping failed, restarting the ping interval again ...");
		final PingManager pingManager = PingManager.getInstanceFor(connection);
		pingManager.setPingInterval(100);

	}

}
