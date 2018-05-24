package com.copart.framework.fcm.extension;

import org.jivesoftware.smackx.gcm.provider.GcmExtensionProvider;

public class FcmExtensionProvider extends GcmExtensionProvider {

	@Override
	public FcmPacketExtension from(String json) {
		return new FcmPacketExtension(json);
	}
}
