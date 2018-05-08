package com.copart.poc.fcm.application;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Application {

	public static void main(String[] args) {

		try {
			String fcmKey = "AAAAMTiZxJk:APA91bHU6husaT784BTJvZsLhxT9eCqUZACbT4fpHS56BCczdgFA5KfjAytFuBsDYHyEbnEDMX_6rhj8pQhx5EYO0vsvsZAO0w2MfNQzY307luiUZpiABf-74ervMU7XJsI44UW4cCT1";
			String tokenID = "c8h-wLBErmg:APA91bECQ6C33AIGznRxxAxqFy9y0QH3ygCbRCdA1jLrrFcs4qNc8-C659jFTY7RuHmEdFXv4e-iPOWpYKMkMiOP7hAjwW_djGHvUFf-4-tETBqofzOHuNszuQ3iVkBojeCA7bEnKmMY";
			String androidFcmUrl = "https://fcm.googleapis.com/fcm/send";

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("Authorization", "key=" + fcmKey);
			httpHeaders.set("Content-Type", "application/json");

			JSONObject body = new JSONObject();
			body.put("to", tokenID);
			body.put("priority", "high");
			//body.put("delivery_receipt_requested", true);
			
			JSONObject notification = new JSONObject();
			notification.put("title", "FCM Notification");
			notification.put("body", "Happy Message !!!!!");

			JSONObject data = new JSONObject();
			data.put("Key-1", "FCM Data 1");
			data.put("Key-2", "FCM Data 2");

			body.put("notification", notification);
			body.put("data", data);
			
			JSONObject json = new JSONObject(body.toString()); // Convert text to object
			System.out.println(json); // Print it with specified indentation

			HttpEntity<String> httpEntity = new HttpEntity<String>(body.toString(), httpHeaders);
			String response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
			System.out.println(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
