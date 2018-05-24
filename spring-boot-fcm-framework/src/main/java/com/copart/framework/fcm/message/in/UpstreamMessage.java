package com.copart.framework.fcm.message.in;

import java.util.Map;

import com.copart.framework.fcm.message.util.MessageUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpstreamMessage {

	@JsonProperty(value = "from")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String from;

	@JsonProperty(value = "category")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String category;

	@JsonProperty(value = "message_id")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String messageId;

	@JsonProperty(value = "dataPayload")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, Object> dataPayload;

	@JsonProperty(value = "time_to_live")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Integer timeToLive;

	@JsonProperty(value = "acknowledgement")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Acknowledgement acknowledgement;

	public Acknowledgement getAcknowledgement() {
		if (this.from == null || this.messageId == null) {
			throw new RuntimeException(
					"Either there is no user to send the Acknowledgement or the Message Id is absent.");
		}
		return new Acknowledgement(this.from, this.messageId, "ack");
	}

	protected UpstreamMessage() {
	}

	protected UpstreamMessage(String from, String category, String messageId) {
		this.from = from;
		this.category = category;
		this.messageId = messageId;
	}

	protected UpstreamMessage(Builder builder) {
		this.from = builder.from;
		this.messageId = builder.messageId;
		this.category = builder.category;
		this.dataPayload = builder.dataPayload;
		this.timeToLive = builder.timeToLive;
	}

	public String getFrom() {
		return from;
	}

	public String getCategory() {
		return category;
	}

	public String getMessageId() {
		return messageId;
	}

	public Map<String, Object> getDataPayload() {
		return dataPayload;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	@Override
	public String toString() {
		return MessageUtils.fromObjectToString(this);
	}

	public static class Builder {

		private String messageId;
		private String from;
		private String category;
		private Map<String, Object> dataPayload;
		private Integer timeToLive;

		protected Builder(String messageId, String from, String category) {
			this.messageId = messageId;
			this.from = from;
			this.category = category;
		}

		public Builder setFrom(String from) {
			this.from = from;
			return this;
		}

		public Builder setMessageId(String messageId) {
			this.messageId = messageId;
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

		public Builder setTimeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
			return this;
		}

		// Factory with compulsory fields for a Notification message
		public static Builder newBuilder(String messageId, String from, String category) {
			return new Builder(messageId, from, category);
		}

		public UpstreamMessage build() {
			return new UpstreamMessage(this);
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Acknowledgement {

		@JsonProperty(value = "to")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private String to;

		@JsonProperty(value = "message_id")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private String messageId;

		@JsonProperty(value = "message_type")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private String messageType;

		protected Acknowledgement() {
		}

		public Acknowledgement(String to, String messageId, String messageType) {
			this.to = to;
			this.messageId = messageId;
			this.messageType = messageType;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public String getMessageType() {
			return messageType;
		}

		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}

		@Override
		public String toString() {
			return MessageUtils.fromObjectToString(this);
		}
	}
}
