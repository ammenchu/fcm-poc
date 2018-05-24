package com.copart.framework.fcm.message.out;

import com.copart.framework.fcm.message.util.MessageUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class DownstreamMessageRequest {

	@JsonProperty(value = "to")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String to;

	@JsonProperty(value = "condition")
	private String condition;

	@JsonProperty(value = "message_id")
	private String messageId;

	@JsonProperty(value = "collapse_key")
	private String collapseKey;

	@JsonProperty(value = "priority")
	private String priority;

	@JsonProperty(value = "content_available")
	private Boolean contentAvailable;

	@JsonProperty(value = "time_to_live")
	private Integer timeToLive;

	@JsonProperty(value = "delivery_receipt_requested")
	private Boolean deliveryReceiptRequested;

	@JsonProperty(value = "dry_run")
	private Boolean dryRun;

	protected DownstreamMessageRequest(String to, String messageId) {
		this.to = to;
		this.messageId = messageId;
	}

	protected DownstreamMessageRequest(Builder builder) {
		this.to = builder.to;
		this.condition = builder.condition;
		this.messageId = builder.messageId;
		this.collapseKey = builder.collapseKey;
		this.priority = builder.priority;
		this.contentAvailable = builder.contentAvailable;
		this.timeToLive = builder.timeToLive;
		this.deliveryReceiptRequested = builder.deliveryReceiptRequested;
		this.dryRun = builder.dryRun;
	}

	protected DownstreamMessageRequest() {
	}

	@Override
	public String toString() {
		// If there is no message ID at this point, we have simply not been
		// provided one. Use the UUID Type 1 ID generator to generate one.
		if (this.messageId == null) {
			messageId = MessageUtils.getUniqueMessageId();
		}
		return MessageUtils.fromObjectToString(this);
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public String getCondition() {
		return condition;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getCollapseKey() {
		return collapseKey;
	}

	public String getPriority() {
		return priority;
	}

	public Boolean isContentAvailable() {
		return contentAvailable;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	public Boolean isDeliveryReceiptRequested() {
		return deliveryReceiptRequested;
	}

	public void setDeliveryReceiptRequested(Boolean deliveryReceiptRequested) {
		this.deliveryReceiptRequested = deliveryReceiptRequested;
	}

	public Boolean isDryRun() {
		return dryRun;
	}

	public void setDryRun(Boolean dryRun) {
		this.dryRun = dryRun;
	}

	public abstract static class Builder {
		private String to;
		private String messageId;
		private String condition;
		private String collapseKey;
		private String priority;
		private Boolean contentAvailable;
		private Integer timeToLive;
		private Boolean deliveryReceiptRequested;
		private Boolean dryRun;

		protected abstract Builder getThis();

		protected Builder(String to, String messageId) {
			this.to = to;
			this.messageId = messageId;

			assignMessageId();
		}

		public Builder setTo(String to) {
			this.to = to;
			return getThis();
		}

		public Builder setMessageId(String messageId) {
			this.messageId = messageId;
			return getThis();
		}

		public Builder setCondition(String condition) {
			this.condition = condition;
			return getThis();
		}

		public Builder setCollapseKey(String collapseKey) {
			this.collapseKey = collapseKey;
			return getThis();
		}

		public Builder setPriority(String priority) {
			this.priority = priority;
			return getThis();
		}

		public Builder setContentAvailable(Boolean contentAvailable) {
			this.contentAvailable = contentAvailable;
			return getThis();
		}

		public Builder setTimeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
			return getThis();
		}

		public Builder setDeliveryReceiptRequested(Boolean deliveryReceiptRequested) {
			this.deliveryReceiptRequested = deliveryReceiptRequested;
			return getThis();
		}

		public Builder setDryRun(Boolean dryRun) {
			this.dryRun = dryRun;
			return getThis();
		}

		private void assignMessageId() {
			if (messageId == null) {
				messageId = MessageUtils.getUniqueMessageId();
			}
		}

	}
}