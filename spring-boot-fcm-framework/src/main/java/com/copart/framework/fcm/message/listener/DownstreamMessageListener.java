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
	public void onReceiveMessage(DownstreamMessage downstreamMessageResponse) {
		super.onReceiveMessage(downstreamMessageResponse);
		if (downstreamMessageResponse.getMessageType().equals("ack")) {
			handleAckReceipt(downstreamMessageResponse);
		} else {
			handleNackReceipt(downstreamMessageResponse);
		}
	}

	/**
	 * Handles a NACK message from FCM
	 */
	public void handleNackReceipt(DownstreamMessage downstreamMessageResponse) {
		logger.info("=== Nack receipt received for a Downstream Message sent. ===");
		String errorCode = downstreamMessageResponse.getError();

		if (errorCode == null) {
			logger.info("Received null FCM Error Code");
			return;
		}

		switch (errorCode) {
		case INVALID_JSON:
			handleUnrecoverableFailure(downstreamMessageResponse);
			break;
		case BAD_REGISTRATION:
			handleUnrecoverableFailure(downstreamMessageResponse);
			break;
		case DEVICE_UNREGISTERED:
			handleUnrecoverableFailure(downstreamMessageResponse);
			break;
		case BAD_ACK:
			handleUnrecoverableFailure(downstreamMessageResponse);
			break;
		case SERVICE_UNAVAILABLE:
			handleServerFailure(downstreamMessageResponse);
			break;
		case INTERNAL_SERVER_ERROR:
			handleServerFailure(downstreamMessageResponse);
			break;
		case DEVICE_MESSAGE_RATE_EXCEEDED:
			handleUnrecoverableFailure(downstreamMessageResponse);
			break;
		case TOPICS_MESSAGE_RATE_EXCEEDED:
			handleUnrecoverableFailure(downstreamMessageResponse);
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
	public void handleAckReceipt(DownstreamMessage downstreamMessageResponse) {
		logger.info("=== Ack receipt received for a Downstream Message sent. ===");
	}

	public void handleServerFailure(DownstreamMessage downstreamMessageResponse) {
		// TODO: Resend the message
		logger.info("Server error: " + downstreamMessageResponse.getError() + " -> "
				+ downstreamMessageResponse.getErrorDescription());

	}

	public void handleUnrecoverableFailure(DownstreamMessage downstreamMessageResponse) {
		// TODO: handle the unrecoverable failure
		logger.info("Unrecoverable error: " + downstreamMessageResponse.getError() + " -> "
				+ downstreamMessageResponse.getErrorDescription());
	}

	public void handleConnectionDrainingFailure() {
		// TODO: handle the connection draining failure. Force reconnect?
		logger.info("FCM Connection is draining!");
	}
}
