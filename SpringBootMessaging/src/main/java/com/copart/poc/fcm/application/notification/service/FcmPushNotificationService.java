package com.copart.poc.fcm.application.notification.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FcmPushNotificationService {

	@Autowired
    private Environment environment;
	
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + environment.getProperty("fcm.poc.serverkey")));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(environment.getProperty("fcm.poc.api.url"), entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
