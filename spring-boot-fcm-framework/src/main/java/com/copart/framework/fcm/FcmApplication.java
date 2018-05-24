package com.copart.framework.fcm;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.copart.framework.fcm.callback.FcmStanzaListener;
import com.copart.framework.fcm.client.FcmCcsClient;
import com.copart.framework.fcm.properties.ApplicationProperties;

@SpringBootApplication
public class FcmApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcmApplication.class, args);
	}

//	@Autowired
//	private ApplicationProperties properties;
//
//	@Autowired
//	private FcmStanzaListener fcmStanzaListener;
//
//	@PostConstruct
//	public void startXMPPServer() {
//		FcmCcsClient fcmCcsClient = FcmCcsClient.createClient(properties.getSenderId(), properties.getServerKey(),
//				true);
//		fcmCcsClient.setFcmStanzaListener(fcmStanzaListener);
//		fcmCcsClient.connect();
//	}
}
