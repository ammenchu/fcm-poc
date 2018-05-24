package com.copart.framework.fcm.source.viewer;

import static com.copart.framework.fcm.message.util.Constants.FCM_NAMESPACE;

import java.util.Map;

import org.jivesoftware.smack.packet.Message;

import com.copart.framework.fcm.extension.FcmPacketExtension;
import com.copart.framework.fcm.message.util.MessageUtils;

public class FcmSourceViewer implements SourceViewer<Message> {

	private String sourceAsString;
	private Map<String, Object> sourceAsMap;
	private Message messageReceived;

	private final Object lock = new Object();

	public FcmSourceViewer(Message messageReceived) {
		this.messageReceived = messageReceived;
		resetSources();
	}

	@Override
	public void setSource(Message messageReceived) {
		this.messageReceived = messageReceived;
		resetSources();
	}

	@Override
	public Message viewSource() {
		return messageReceived;
	}

	public String getSourceAsJSONString() {
		// Lazy get
		synchronized (lock) {
			if (messageReceived != null) {
				if (sourceAsString == null) {
					FcmPacketExtension gcmPacket = (FcmPacketExtension) messageReceived.getExtension(FCM_NAMESPACE);
					sourceAsString = gcmPacket.getJson();
				}
			}
		}
		return sourceAsString;
	}

	public Map<String, Object> getSourceAsMap() {

		synchronized (lock) {
			if (messageReceived != null) {
				// If the source string has not been get, get() it
				if (sourceAsString == null) {
					getSourceAsJSONString();
				}
				if (sourceAsMap == null) {
					sourceAsMap = MessageUtils.getAttributeMapFromJsonString(sourceAsString);
				}
			}
		}
		return sourceAsMap;
	}

	private void resetSources() {
		// Explicit setting of other forms of Source to null
		// so that they can be recalculated for new main source.
		synchronized (lock) {
			sourceAsString = null;
			sourceAsMap = null;
		}
	}
}
