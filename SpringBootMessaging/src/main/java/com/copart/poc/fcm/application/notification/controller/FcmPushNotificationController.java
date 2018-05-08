package com.copart.poc.fcm.application.notification.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.copart.poc.fcm.application.notification.service.FcmPushNotificationService;

@RestController
public class FcmPushNotificationController {

	@Autowired
	FcmPushNotificationService fcmPushNotificationService;
	
	@Autowired
    private Environment environment;

	@RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> send() throws JSONException {

		JSONObject payload = new JSONObject();
		payload.put("to", environment.getProperty("fcm.poc.tokenId"));
		payload.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", "FCM Notification");
		notification.put("body", "Happy Message !!!!!");

		JSONObject data = new JSONObject();
		data.put("Key-1", "FCM Data 1");
		data.put("Key-2", "FCM Data 2");

		payload.put("notification", notification);
		payload.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(payload.toString());

		CompletableFuture<String> pushNotification = fcmPushNotificationService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();

			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}
}
