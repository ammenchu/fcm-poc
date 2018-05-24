package com.copart.framework.fcm.endpoint;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.copart.framework.fcm.client.FcmCcsClient;
import com.copart.framework.fcm.message.in.DownstreamMessage;
import com.copart.framework.fcm.message.listener.DownstreamMessageListener;
import com.copart.framework.fcm.message.out.Message;

@RestController
public class FcmDownstreamController {

	private static final Logger logger = Logger.getLogger(FcmDownstreamController.class.getSimpleName());

	@Autowired
	private FcmCcsClient fcmCcsClient;

	@Autowired
	private DownstreamMessageListener downstreamMessageListener;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Poc application: FCM downstrean and upstream using XMPP connection";
	}
	
	@RequestMapping("/{number}")
	@ResponseBody
	String homeTest(@PathVariable Long number) {
		return "value => "+number;
	}

	@RequestMapping(path = "/send", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody DeferredResult<DownstreamMessage> sendMessage(@RequestBody Message message) {

		String firebaseTokenReceiver = message.getTo();

		if (firebaseTokenReceiver == null) {
			logger.warning("Account with the User ID: " + message.getTo() + "doesn't exist");
			return null;
		}

		// Lets not block this thread for this request anymore
		DeferredResult<DownstreamMessage> deferredResult = new DeferredResult<DownstreamMessage>();

		// Maintain this deferred result with the callback for later setting.
		downstreamMessageListener.setDeferredResultHolder(deferredResult);

		fcmCcsClient.sendAsync(Message.toFCMMessage(message));

		return deferredResult;
	}
}
