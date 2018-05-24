package com.copart.framework.fcm.message.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MessageUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static Map<String, Object> toAttributeMap(Object object) {
		return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
		});
	}

	public static Map<String, Object> getAttributeMapFromJsonString(String json) {
		Map<String, Object> map;
		try {
			map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("There was a problem parsing the JSON string.");
		}
		return map;
	}

	public static String getPrettyPrintedJson(Object toBePrettyPrinted) {
		String prettyPrint = null;
		try {
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			prettyPrint = objectMapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(objectMapper.readValue(toBePrettyPrinted.toString(), Object.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prettyPrint;
	}

	public static String fromObjectToString(Object object) {
		String json;
		try {
			json = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was a problem processing Json.");
		}
		return json;
	}

	public static <T> T fromStringToMessage(String source, Class<T> clazz) {
		T t;
		try {
			t = objectMapper.readValue(source, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was a problem processing Json.");
		}
		return t;
	}

	/**
	 * Creates a JSON encoded ACK message for a received incoming message.
	 *
	 * Use UpstreamMessage.getAcknowledgement() to get the Acknowledgement now.
	 */
	@Deprecated
	public static String createJsonAck(String to, String messageId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message_type", "ack");
		map.put("to", to);
		map.put("message_id", messageId);

		return createJsonMessage(map);
	}

	public static String createJsonMessage(Map<String, Object> jsonMap) {
		String json;
		try {
			json = objectMapper.writeValueAsString(jsonMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("There was a problem processing Json.");
		}
		return json;
	}

	public static String getUniqueMessageId() {
		return UUID.randomUUID().toString();
	}

}
