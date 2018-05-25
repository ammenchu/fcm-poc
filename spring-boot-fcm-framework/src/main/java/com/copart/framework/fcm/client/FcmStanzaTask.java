package com.copart.framework.fcm.client;

public class FcmStanzaTask implements Runnable {

	private FcmCcsClient client;
	private String message;

	public FcmStanzaTask(FcmCcsClient client) {
		if (client == null) {
			throw new IllegalStateException("Client is uninitialized.");
		}
		this.client = client;
	}

	public FcmStanzaTask(FcmCcsClient client, String message) {
		this(client);
		this.message = message;
	}

	public FcmCcsClient getClient() {
		return client;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		if (message == null || message.isEmpty()) {
			throw new RuntimeException("Message to be sent cannot be null or empty.");
		}
		client.send(message);
	}
}