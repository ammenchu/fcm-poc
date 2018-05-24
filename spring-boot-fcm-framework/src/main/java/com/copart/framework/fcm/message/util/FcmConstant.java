package com.copart.framework.fcm.message.util;

import java.util.logging.Logger;

import com.copart.framework.fcm.extension.FcmPacketExtension;

public final class FcmConstant {

	private static Logger logger = Logger.getLogger(FcmConstant.class.getSimpleName());

	static {
		// Inform about default port
		logger.info("Default FCM port has been set to 5236(for Testing).");
	}

	// For the FCM connection
	public static final String HOST_ADDRESS = "fcm-xmpp.googleapis.com";
	public static final String HOST = "fcm-xmpp.googleapis.com";
	public static final String DOMAIN = "googleapis.com";

	// 5236 is for testing purposes; 5235 is for production;
	// Default is set to TESTING.
	public static final int FCM_PORT_FOR_TESTING = 5236;
	public static final int FCM_PORT_FOR_PRODUCTION = 5235;
	public static final int FCM_PORT = FCM_PORT_FOR_TESTING;

	public static final String FCM_ELEMENT = FcmPacketExtension.ELEMENT;
	public static final String FCM_NAMESPACE = FcmPacketExtension.NAMESPACE;
	public static final String FCM_SERVER_CONNECTION_ENDPOINT = "gcm.googleapis.com";

}
