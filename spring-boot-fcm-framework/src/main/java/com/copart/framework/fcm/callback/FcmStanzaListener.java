package com.copart.framework.fcm.callback;

import java.util.Map;
import java.util.logging.Logger;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.copart.framework.fcm.message.in.DeliveryReceiptOrControlMessage;
import com.copart.framework.fcm.message.in.DownstreamMessage;
import com.copart.framework.fcm.message.in.UpstreamMessage;
import com.copart.framework.fcm.message.listener.DeliveryReceiptOrControlMessageListener;
import com.copart.framework.fcm.message.listener.DownstreamMessageListener;
import com.copart.framework.fcm.message.listener.UpstreamMessageListener;
import com.copart.framework.fcm.message.util.MessageUtils;
import com.copart.framework.fcm.source.viewer.FcmSourceViewer;

@Component
public class FcmStanzaListener implements StanzaListener {

	private static final Logger logger = Logger.getLogger(FcmStanzaListener.class.getSimpleName());

	@Autowired
	private DeliveryReceiptOrControlMessageListener dvryRcptOrCntlMessageListener;

	@Autowired
	private UpstreamMessageListener upStreamMessageListener;

	@Autowired
	private DownstreamMessageListener downstreamMessageListener;

	public void setDvryRcptOrCntlMessageListener(
			DeliveryReceiptOrControlMessageListener dvryRcptOrCntlMessageListener) {
		this.dvryRcptOrCntlMessageListener = dvryRcptOrCntlMessageListener;
	}

	public void setUpStreamMessageListener(UpstreamMessageListener upStreamMessageListener) {
		this.upStreamMessageListener = upStreamMessageListener;
	}

	public void setDownstreamMessageListener(DownstreamMessageListener downstreamMessageListener) {
		this.downstreamMessageListener = downstreamMessageListener;
	}

	@Override
	public void processStanza(Stanza incomingStanza) throws SmackException.NotConnectedException, InterruptedException {

		// logger.log(Level.INFO, "Received : " + incomingStanza.toXML());

		final FcmSourceViewer fcmMessageSourceViewer = new FcmSourceViewer((Message) incomingStanza);

		Map<String, Object> sourceAsMap = fcmMessageSourceViewer.getSourceAsMap();
		String sourceAsString = fcmMessageSourceViewer.getSourceAsJSONString();

		String messageTypeString = (String) sourceAsMap.get("message_type");

		if (messageTypeString == null) {
			
			// UpstreamMessage type
			UpstreamMessage response = MessageUtils.fromStringToMessage(sourceAsString, UpstreamMessage.class);

			upStreamMessageListener.setSource(fcmMessageSourceViewer);

			upStreamMessageListener.onReceiveMessage(response);

		} else if (messageTypeString.equals("ack") || messageTypeString.equals("nack")) {
			
			// A DownstreamMessageResponse type message
			DownstreamMessage response = MessageUtils.fromStringToMessage(sourceAsString, DownstreamMessage.class);

			downstreamMessageListener.setSource(fcmMessageSourceViewer);

			downstreamMessageListener.onReceiveMessage(response);

		} else if (messageTypeString.equals("receipt") || messageTypeString.equals("control")) {
			
			// Ccs to App server messages, i.e, DeliveryReceiptOrControlMessage type
			DeliveryReceiptOrControlMessage response = MessageUtils.fromStringToMessage(sourceAsString,
					DeliveryReceiptOrControlMessage.class);

			dvryRcptOrCntlMessageListener.setSource(fcmMessageSourceViewer);

			dvryRcptOrCntlMessageListener.onReceiveMessage(response);

		} else {

			logger.severe("An unknown message type received from CCS. Does"
					+ "Firebase have a new type we don't know about?...Or, if not then"
					+ "it is truly an unknown type.");
			throw new RuntimeException("Unknown Message type received from Ccs.");
		}
	}

}
