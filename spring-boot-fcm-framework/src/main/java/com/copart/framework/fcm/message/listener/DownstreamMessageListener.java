package com.copart.framework.fcm.message.listener;

import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.BAD_ACK;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.BAD_REGISTRATION;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.CONNECTION_DRAINING;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.DEVICE_MESSAGE_RATE_EXCEEDED;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.DEVICE_UNREGISTERED;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.INTERNAL_SERVER_ERROR;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.INVALID_JSON;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.SERVICE_UNAVAILABLE;
import static com.copart.framework.fcm.message.in.DownstreamMessage.ErrorResponseCodes.TOPICS_MESSAGE_RATE_EXCEEDED;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.copart.framework.fcm.message.in.DownstreamMessage;

@Component
public class DownstreamMessageListener extends DeferringFcmMessageListener<DownstreamMessage> {

	private static final Logger logger = Logger
			.getLogger(DownstreamMessageListener.class.getSimpleName());

	@Override
	public void onReceiveMessage(DownstreamMessage downstreamMessage) {
		super.onReceiveMessage(downstreamMessage);
		if (downstreamMessage.getMessageType().equals("ack")) {
			handleAckReceipt(downstreamMessage);
		} else {
			handleNackReceipt(downstreamMessage);
		}
	}

	/**
	 * Handles a NACK message from FCM
	 */
	public void handleNackReceipt(DownstreamMessage downstreamMessage) {
		logger.info("<<<< Nack receipt received for a Downstream Message sent. <<<<");
		String errorCode = downstreamMessage.getError();

		if (errorCode == null) {
			logger.info("Received null FCM Error Code");
			return;
		}

		switch (errorCode) {
		case INVALID_JSON:
			handleUnrecoverableFailure(downstreamMessage);
			break;
		case BAD_REGISTRATION:
			handleUnrecoverableFailure(downstreamMessage);
			break;
		case DEVICE_UNREGISTERED:
			handleUnrecoverableFailure(downstreamMessage);
			break;
		case BAD_ACK:
			handleUnrecoverableFailure(downstreamMessage);
			break;
		case SERVICE_UNAVAILABLE:
			handleServerFailure(downstreamMessage);
			break;
		case INTERNAL_SERVER_ERROR:
			handleServerFailure(downstreamMessage);
			break;
		case DEVICE_MESSAGE_RATE_EXCEEDED:
			handleUnrecoverableFailure(downstreamMessage);
			break;
		case TOPICS_MESSAGE_RATE_EXCEEDED:
			handleUnrecoverableFailure(downstreamMessage);
			break;
		case CONNECTION_DRAINING:
			handleConnectionDrainingFailure();
			break;
		default:
			logger.info("Received unknown FCM Error Code: " + errorCode);
		}
	}

	/**
	 * Handles an ACK message from FCM
	 */
	public void handleAckReceipt(DownstreamMessage downstreamMessage) {
		logger.info("<<<< Ack receipt received for a Downstream Message sent. <<<<");
	}

	public void handleServerFailure(DownstreamMessage downstreamMessage) {
		// TODO: Resend the message
		logger.info("Server error: " + downstreamMessage.getError() + " -> "
				+ downstreamMessage.getErrorDescription());

	}

	public void handleUnrecoverableFailure(DownstreamMessage downstreamMessage) {
		// TODO: handle the unrecoverable failure
		logger.info("Unrecoverable error: " + downstreamMessage.getError() + " -> "
				+ downstreamMessage.getErrorDescription());
	}

	public void handleConnectionDrainingFailure() {
		// TODO: handle the connection draining failure. Force reconnect?
		logger.info("FCM Connection is draining!");
	}
}
