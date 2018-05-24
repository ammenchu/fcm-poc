package com.copart.framework.fcm.message.in;

import com.copart.framework.fcm.message.util.MessageUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownstreamMessage {

	@JsonProperty(value = "from")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String from;

	@JsonProperty(value = "message_id")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String messageId;

	@JsonProperty(value = "message_type")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String messageType;

	@JsonProperty(value = "registration_id")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String registrationId;

	@JsonProperty(value = "error")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String error;

	@JsonProperty(value = "error_description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String errorDescription;

	protected DownstreamMessage(String from, String messageId, String messageType) {
		this.from = from;
		this.messageId = messageId;
		this.messageType = messageType;
	}

	protected DownstreamMessage() {

	}

	protected DownstreamMessage(Builder builder) {
		this.from = builder.from;
		this.messageId = builder.messageId;
		this.messageType = builder.messageType;
		this.error = builder.error;
		this.errorDescription = builder.errorDescription;
		this.registrationId = builder.registrationId;
	}

	public String getFrom() {
		return from;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public String getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	@Override
	public String toString() {
		return MessageUtils.fromObjectToString(this);
	}

	public static class Builder {

		private String from;
		private String messageId;
		private String messageType;
		private String registrationId;
		private String error;
		private String errorDescription;

		protected Builder(String from, String messageId, String messageType) {
			this.from = from;
			this.messageId = messageId;
			this.messageType = messageType;
		}

		public Builder setFrom(String from) {
			this.from = from;
			return this;
		}

		public Builder setMessageId(String messageId) {
			this.messageId = messageId;
			return this;
		}

		public Builder setMessageType(String messageType) {
			this.messageType = messageType;
			return this;
		}

		public Builder setRegistrationId(String registrationId) {
			this.registrationId = registrationId;
			return this;
		}

		public Builder setError(String error) {
			this.error = error;
			return this;
		}

		public Builder setErrorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
			return this;
		}

		// Factory with compulsory fields for a Notification message
		public static Builder newBuilder(String from, String messageId, String messageType) {
			return new Builder(from, messageId, messageType);
		}

		public DownstreamMessage build() {
			return new DownstreamMessage(this);
		}
	}

	public static final class ErrorResponseCodes {

		public static final String INVALID_JSON = "INVALID_JSON";

		public static final String BAD_REGISTRATION = "BAD_REGISTRATION";

		public static final String DEVICE_UNREGISTERED = "DEVICE_UNREGISTERED";

		public static final String BAD_ACK = "BAD_ACK";

		public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";

		public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

		public static final String DEVICE_MESSAGE_RATE_EXCEEDED = "DEVICE_MESSAGE_RATE_EXCEEDED";

		public static final String TOPICS_MESSAGE_RATE_EXCEEDED = "TOPICS_MESSAGE_RATE_EXCEEDED";

		public static final String CONNECTION_DRAINING = "CONNECTION_DRAINING";

	}
}
