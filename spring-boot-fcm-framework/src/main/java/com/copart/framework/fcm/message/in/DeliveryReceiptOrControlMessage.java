package com.copart.framework.fcm.message.in;

import java.util.Map;

import com.copart.framework.fcm.message.util.MessageUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryReceiptOrControlMessage {

	@JsonProperty(value = "message_type")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String messageType;

	@JsonProperty(value = "from")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String from;

	@JsonProperty(value = "message_id")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String messageId;

	@JsonProperty(value = "category")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String category;

	@JsonProperty(value = "data")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, Object> dataPayload;

	@JsonProperty(value = "control_type")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String controlType;

	@JsonProperty(value = "time_to_live")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Integer timeToLive;

	protected DeliveryReceiptOrControlMessage() {
	}

	protected DeliveryReceiptOrControlMessage(String messageType) {
		this.messageType = messageType;
	}

	protected DeliveryReceiptOrControlMessage(Builder builder) {
		this.messageType = builder.messageType;
		this.category = builder.category;
		this.controlType = builder.controlType;
		this.dataPayload = builder.dataPayload;
		this.from = builder.from;
		this.messageId = builder.messageId;
		this.timeToLive = builder.timeToLive;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getFrom() {
		return from;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getCategory() {
		return category;
	}

	public Map<String, Object> getDataPayload() {
		return dataPayload;
	}

	public String getControlType() {
		return controlType;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	@Override
	public String toString() {
		return MessageUtils.fromObjectToString(this);
	}

	public static class Builder {

		private String messageType;
		private String messageId;
		private String from;
		private String category;
		private Map<String, Object> dataPayload;
		private String controlType;
		private Integer timeToLive;

		protected Builder(String messageType) {
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

		public Builder setRegistrationId(String category) {
			this.category = category;
			return this;
		}

		public Builder setError(Map<String, Object> data) {
			this.dataPayload = data;
			return this;
		}

		public Builder setErrorDescription(String controlType) {
			this.controlType = controlType;
			return this;
		}

		public Builder setTimeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
			return this;
		}

		// Factory with compulsory fields for a Notification message
		public static Builder newBuilder(String messageType) {
			return new Builder(messageType);
		}

		public DeliveryReceiptOrControlMessage build() {
			return new DeliveryReceiptOrControlMessage(this);
		}
	}
}
