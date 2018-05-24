package com.copart.framework.fcm.message.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.copart.framework.fcm.message.in.DeliveryReceiptOrControlMessage;

@Component
public class DeliveryReceiptOrControlMessageListener
		extends DeferringFcmMessageListener<DeliveryReceiptOrControlMessage> {

	private static final Logger logger = Logger
			.getLogger(DeliveryReceiptOrControlMessageListener.class.getSimpleName());

	@Override
	public void onReceiveMessage(DeliveryReceiptOrControlMessage deliveryReceiptOrControlMessage) {
		super.onReceiveMessage(deliveryReceiptOrControlMessage);
		if (deliveryReceiptOrControlMessage.getMessageType().equals("receipt")) {
			handleDeliveryReceipt(deliveryReceiptOrControlMessage);
		} else {
			handleControlMessage(deliveryReceiptOrControlMessage);
		}
	}

	/**
	 * Handles a Control message from FCM
	 */
	protected void handleControlMessage(DeliveryReceiptOrControlMessage deliveryReceiptOrControlMessage) {

		String controlType = deliveryReceiptOrControlMessage.getControlType();

		if (controlType.equals("CONNECTION_DRAINING")) {
			handleConnectionDrainingFailure();
		} else {
			logger.log(Level.INFO, "Received unknown FCM Control message: " + controlType);
		}
	}

	/**
	 * Handles a Delivery Receipt message from FCM (when a device confirms that it
	 * received a particular message)
	 */
	protected void handleDeliveryReceipt(DeliveryReceiptOrControlMessage deliveryReceiptOrControlMessage) {
		logger.info("=== Delivery Receipt received. ===");
	}

	protected void handleConnectionDrainingFailure() {
		logger.info("FCM Connection is draining!");
	}
}
