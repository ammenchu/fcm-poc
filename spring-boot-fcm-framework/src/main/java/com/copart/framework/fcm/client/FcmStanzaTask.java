package com.copart.framework.fcm.client;

public class FcmStanzaTask implements Runnable {

	private FcmCcsClient client;
	private String jsonMsg;

	public FcmStanzaTask(FcmCcsClient client) {
		if (client == null) {
			throw new IllegalStateException("Client is uninitialized.");
		}
		this.client = client;
	}

	public FcmStanzaTask(FcmCcsClient client, String jsonMsg) {
		this(client);
		this.jsonMsg = jsonMsg;
	}

	public FcmCcsClient getClient() {
		return client;
	}

	public String getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	@Override
	public void run() {
		if (jsonMsg == null || jsonMsg.isEmpty()) {
			throw new RuntimeException("Message to be sent cannot be null or empty.");
		}
		client.send(jsonMsg);
	}
}