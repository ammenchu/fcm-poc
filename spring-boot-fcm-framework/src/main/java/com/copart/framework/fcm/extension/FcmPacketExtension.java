package com.copart.framework.fcm.extension;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.gcm.packet.GcmPacketExtension;
import org.jivesoftware.smackx.pubsub.util.XmlUtils;

import java.util.logging.Logger;

public class FcmPacketExtension extends GcmPacketExtension {

	private static final Logger logger = Logger.getLogger(FcmPacketExtension.class.getSimpleName());

	// Reserved in case Google decides to change them for Firebase.
	public static final String ELEMENT = "gcm";
	public static final String NAMESPACE = "google:mobile:data";

	public FcmPacketExtension(String json) {
		super(json);
	}

	public Message toMessage() {

		Message message = new Message();
		message.addExtension(this);

		logger.info("The Message(Stanza) is:");
		XmlUtils.prettyPrint(null, message.toXML().toString());

		return message;
	}
}
