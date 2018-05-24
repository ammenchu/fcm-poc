package com.copart.framework.fcm.message.out;

import java.io.Serializable;
import java.util.UUID;

import com.copart.framework.fcm.message.util.MessageUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer messageType;

	/**
	 * No explicit "content" - would be sent as a FCM Notification for a tutoring
	 * request.
	 */
	public static final int TUTORING_REQUEST = 0x0002;

	/** Would be sent as a FCM Notification */
	public static final int CHAT_TEXT_MESSAGE = 0x0004;

	/** A general FCM Data message */
	public static final int FCM_DATA_MESSAGE = 0x0008;

	/** A general FCM Notification message */
	public static final int FCM_NOTIFICATION_MESSAGE = 0x0016;

	/** User ID of the Sender - currently Email based UUID */
	private String from;
	/** User ID of the Receiver - currently Email based UUID */
	private String to;

	/**
	 * A FCM Data Message(payload)
	 */
	private DataMessage dataMessage;

	/**
	 * A FCM Notification Message(payload)
	 */
	private NotificationMessage notificationMessage;

	public Message(String from, String to, int messageType) {
		// Validate input
		validateInput(messageType);
		// from should be a UUID
		validateInput(from);
		// validateInput(to);

		this.from = from;
		this.to = to;
		this.messageType = messageType;
	}

	@Override
	public String toString() {

		return MessageUtils.fromObjectToString(this);
	}

	// Default constructor for Serialization
	protected Message() {
	}

	private void validateInput(String UUID) {
		java.util.UUID.fromString(UUID);
	}

	private void validateInput(int messageType) {
		switch (messageType) {
		case TUTORING_REQUEST:
			break;
		case CHAT_TEXT_MESSAGE:
			break;
		case FCM_DATA_MESSAGE:
			break;
		case FCM_NOTIFICATION_MESSAGE:
			break;
		default:
			throw new IllegalStateException("Invalid message type.");

		}
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		UUID.fromString(from);
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		// UUID.fromString(to);
		this.to = to;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		validateInput(messageType);
		this.messageType = messageType;
	}

	public DataMessage getDataMessage() {
		return dataMessage;
	}

	public void setDataMessage(DataMessage dataMessage) {
		this.dataMessage = dataMessage;
	}

	public NotificationMessage getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(NotificationMessage notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public static DownstreamMessageRequest toFCMMessage(Message message) {

		DownstreamMessageRequest messageToBeSent;

		switch (message.getMessageType()) {

		case TUTORING_REQUEST:
			// message.getNotificationMessage().put("messageType", "TUTORING_REQUEST");
			messageToBeSent = message.getNotificationMessage();
			break;
		case CHAT_TEXT_MESSAGE:
			// message.getNotificationMessage().put("messageType", "CHAT_TEXT_MESSAGE");
			messageToBeSent = message.getNotificationMessage();
			break;
		case FCM_DATA_MESSAGE:
			messageToBeSent = message.getDataMessage();
			break;
		case FCM_NOTIFICATION_MESSAGE:
			messageToBeSent = message.getNotificationMessage();
			break;
		default:
			throw new IllegalStateException("Invalid message type.");

		}
		messageToBeSent.setDeliveryReceiptRequested(true);
		messageToBeSent.setTo(message.getTo());

		return messageToBeSent;
	}
}
