package com.copart.framework.fcm.message.listener;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.copart.framework.fcm.source.viewer.FcmSourceViewer;
import com.copart.framework.fcm.source.viewer.SourceViewer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DeferringFcmMessageListener<Message>
		implements GenericFcmMessageListener<Message>, SourceViewer<FcmSourceViewer> {

	private static final Logger logger = Logger.getLogger(DeferringFcmMessageListener.class.getSimpleName());

	private DeferredResult<Message> deferredResult;

	ObjectMapper mapper = new ObjectMapper();

	private FcmSourceViewer fcmMessageSourceViewer;

	@Override
	public void setSource(FcmSourceViewer fcmMessageSourceViewer) {
		this.fcmMessageSourceViewer = fcmMessageSourceViewer;
	}

	@Override
	public FcmSourceViewer viewSource() {
		return fcmMessageSourceViewer;
	}

	@Override
	public void onReceiveMessage(Message response) {

		if (response.getClass() != null) {
			logger.info(getMessage(response));
		}
		// Set the deferred result.
		if (deferredResult != null) {
			deferredResult.setResult(response);
		}
	}

	public void setDeferredResultHolder(DeferredResult<Message> result) {
		this.deferredResult = result;
	}

	private String getMessage(Message response) {
		String messgage = "";
		try {
			String clazzName = response.getClass().getSimpleName();
			String prettyJson = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(mapper.readValue(response.toString(), response.getClass()));
			messgage = String.format("\nThe Message received of type - %s is: \n%s", clazzName, prettyJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messgage;
	}
}
