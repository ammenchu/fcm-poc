package com.copart.framework.fcm.message.listener;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.copart.framework.fcm.client.FcmCcsClient;
import com.copart.framework.fcm.message.in.UpstreamMessage;

@Component
public class UpstreamMessageListener extends DeferringFcmMessageListener<UpstreamMessage> {

	private static final Logger logger = Logger.getLogger(UpstreamMessageListener.class.getSimpleName());

	@Autowired
	private FcmCcsClient fcmCcsClient;
	
	@Override
	public void onReceiveMessage(UpstreamMessage upstreamMessage) {
		super.onReceiveMessage(upstreamMessage);
		logger.info("<<<< Upstream message received: <<<<< \n" + this.viewSource().toString());

		processUpstreamMessage(upstreamMessage);

		// Send ACK to Ccs
		logger.info(">>>> Sending Ack to Ccs for the Upstream Message received... >>>> ");
//		FcmCcsClient.getInstance().sendAsync(upstreamMessage.getAcknowledgement().toString());
		fcmCcsClient.sendAsync(upstreamMessage.getAcknowledgement().toString());
	}

	protected void processUpstreamMessage(UpstreamMessage upstreamMessage) {
		// Already logged!
	}
}
