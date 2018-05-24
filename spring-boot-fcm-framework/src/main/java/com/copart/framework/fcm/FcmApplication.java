package com.copart.framework.fcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
