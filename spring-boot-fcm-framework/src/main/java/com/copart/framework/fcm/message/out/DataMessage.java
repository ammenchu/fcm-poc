package com.copart.framework.fcm.message.out;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataMessage extends DownstreamMessageRequest {

	@JsonProperty(value = "data")
	private Map<String, String> dataPayload;

	public Map<String, String> getDataPayload() {
		return dataPayload;
	}

	public void setDataPayload(Map<String, String> dataPayload) {
		this.dataPayload = dataPayload;
	}

	protected DataMessage(Builder builder) {
		super(builder);
		this.dataPayload = builder.dataPayload;
	}

	protected DataMessage() {

	}

	public static class Builder extends DownstreamMessageRequest.Builder {

		private Map<String, String> dataPayload;

		public static Builder newInstance(String to, String messageId, Map<String, String> dataPayload) {
			return new Builder(to, messageId, dataPayload);
		}

		protected Builder(String to, String messageId, Map<String, String> dataPayload) {
			super(to, messageId);
			this.dataPayload = dataPayload;
		}

		// So that the subclasser can make data payload optional
		protected Builder(String to, String messageId) {
			super(to, messageId);
		}

		public Builder setDataPayload(Map<String, String> dataPayload) {
			this.dataPayload = dataPayload;
			return getThis();
		}

		@Override
		protected Builder getThis() {
			return this;
		}

		public DataMessage build() {
			return new DataMessage(this);
		}
	}
}
