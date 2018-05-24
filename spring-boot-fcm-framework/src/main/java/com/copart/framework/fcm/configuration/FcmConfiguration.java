package com.copart.framework.fcm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.copart.framework.fcm.callback.FcmStanzaListener;
import com.copart.framework.fcm.client.FcmCcsClient;
import com.copart.framework.fcm.properties.ApplicationProperties;

@Configuration
public class FcmConfiguration {

	@Bean
	public FcmCcsClient getFcmCcsClient(FcmStanzaListener fcmStanzaListener, ApplicationProperties properties) {
		FcmCcsClient fcmCcsClient = FcmCcsClient.createClient(properties.getSenderId(), properties.getServerKey(),
				true);
		fcmCcsClient.setFcmStanzaListener(fcmStanzaListener);
		fcmCcsClient.connect();
		return fcmCcsClient;
	}
}
