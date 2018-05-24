package com.copart.framework.fcm.message.out;

import java.util.Map;

import com.copart.framework.fcm.message.util.MessageUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationMessage extends DataMessage {

	@JsonProperty(value = "notification")
	private Map<String, Object> notificationPayload;

	protected NotificationMessage(Builder builder) {
		super(builder);
		// Now get the Payload from the Payload POJO in an Attribute Map
		this.notificationPayload = MessageUtils.toAttributeMap(builder.notificationPayload);
	}

	protected NotificationMessage() {
	}

	public static class Builder extends DataMessage.Builder {

		private NotificationPayload notificationPayload;

		// Factory with compulsory fields for a Notification message
		public static Builder newBuilderInstance(String to, String messageId) {
			return new Builder(to, messageId);
		}

		protected Builder(String to, String messageId) {
			super(to, messageId);
		}

		public Builder setNotificationPayload(NotificationPayload notificationPayload) {
			this.notificationPayload = notificationPayload;
			return getThis();
		}

		@Override
		protected Builder getThis() {
			return this;
		}

		public NotificationMessage build() {
			return new NotificationMessage(this);
		}
	}

	public static class NotificationPayload {

		private String title;

		private String body;

		private String icon;

		private String sound;

		private String tag;

		private String color;

		private String click_action;

		private String body_loc_key;

		private String body_loc_args;

		private String title_loc_key;

		private String title_loc_args;

		private Map<String, Object> notificationExtras;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getSound() {
			return sound;
		}

		public void setSound(String sound) {
			this.sound = sound;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getClick_action() {
			return click_action;
		}

		public void setClick_action(String click_action) {
			this.click_action = click_action;
		}

		public String getBody_loc_key() {
			return body_loc_key;
		}

		public void setBody_loc_key(String body_loc_key) {
			this.body_loc_key = body_loc_key;
		}

		public String getBody_loc_args() {
			return body_loc_args;
		}

		public void setBody_loc_args(String body_loc_args) {
			this.body_loc_args = body_loc_args;
		}

		public String getTitle_loc_key() {
			return title_loc_key;
		}

		public void setTitle_loc_key(String title_loc_key) {
			this.title_loc_key = title_loc_key;
		}

		public String getTitle_loc_args() {
			return title_loc_args;
		}

		public void setTitle_loc_args(String title_loc_args) {
			this.title_loc_args = title_loc_args;
		}

		public Map<String, Object> getNotificationExtras() {
			return notificationExtras;
		}

		public void setNotificationExtras(Map<String, Object> notificationExtras) {
			this.notificationExtras = notificationExtras;
		}

		protected NotificationPayload(Builder b) {
			this.notificationExtras = b.notificationExtras;
			this.title = b.title;
			this.body = b.body;
			this.color = b.color;
			this.tag = b.tag;
			this.sound = b.sound;
			this.icon = b.icon;
			this.body_loc_args = b.body_loc_args;
			this.body_loc_key = b.body_loc_key;
			this.click_action = b.click_action;
			this.title_loc_args = b.title_loc_args;
			this.title_loc_key = b.title_loc_key;
		}

		public static class Builder {

			private String title;

			private String body;

			private String icon;

			private String sound;

			private String tag;

			private String color;

			private String click_action;

			private String body_loc_key;

			private String body_loc_args;

			private String title_loc_key;

			private String title_loc_args;

			private Map<String, Object> notificationExtras;

			// Factory with compulsory fields for a Notification message
			public static Builder newBuilder() {
				return new Builder();
			}

			public Builder setTitle(String title) {
				this.title = title;
				return getThis();
			}

			public Builder setBody(String body) {
				this.body = body;
				return getThis();
			}

			public Builder setIcon(String icon) {
				this.icon = icon;
				return getThis();
			}

			public Builder setSound(String sound) {
				this.sound = sound;
				return getThis();
			}

			public Builder setTag(String tag) {
				this.tag = tag;
				return getThis();
			}

			public Builder setColor(String color) {
				this.color = color;
				return getThis();
			}

			public Builder setClick_action(String click_action) {
				this.click_action = click_action;
				return getThis();
			}

			public Builder setBody_loc_key(String body_loc_key) {
				this.body_loc_key = body_loc_key;
				return getThis();
			}

			public Builder setBody_loc_args(String body_loc_args) {
				this.body_loc_args = body_loc_args;
				return getThis();
			}

			public Builder setTitle_loc_key(String title_loc_key) {
				this.title_loc_key = title_loc_key;
				return getThis();
			}

			public Builder setTitle_loc_args(String title_loc_args) {
				this.title_loc_args = title_loc_args;
				return getThis();
			}

			public Builder setNotificationExtras(Map<String, Object> notificationExtras) {
				this.notificationExtras = notificationExtras;
				return getThis();
			}

			protected Builder getThis() {
				return this;
			}

			public NotificationPayload build() {
				return new NotificationPayload(this);
			}
		}
	}
}
