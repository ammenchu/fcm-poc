package com.copart.framework.fcm.message.listener;

public interface GenericFcmMessageListener<Message> {

	void onReceiveMessage(Message message);
}
