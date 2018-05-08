package com.copart.poc.fcm.application.xmppserver.config;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.integration.xmpp.config.XmppConnectionFactoryBean;

@Configuration
@ImportResource("classpath:integration.xml")
public class FcmXmppServerConfiguration {

	@Autowired
    private Environment environment;
	
	@Bean("xmppConnection")
	public XmppConnectionFactoryBean xmppConnectionFactoryBean() {
		
		String senderID = environment.getProperty("fcm.poc.senderId");
		String serverKey = environment.getProperty("fcm.poc.serverkey");

		XMPPTCPConnectionConfiguration connectionConfiguration = XMPPTCPConnectionConfiguration.builder()
				.setServiceName("copart.com")
				.setHost("fcm-xmpp.googleapis.com")
				.setPort(5235)
				.setUsernameAndPassword(senderID + "@gcm.googleapis.com", serverKey)
				.setSecurityMode(SecurityMode.ifpossible)
				.setSendPresence(false)
				.setSocketFactory(SSLSocketFactory.getDefault())
			.build();

		XmppConnectionFactoryBean connectionFactoryBean = new XmppConnectionFactoryBean();
		connectionFactoryBean.setConnectionConfiguration(connectionConfiguration);
		connectionFactoryBean.setSubscriptionMode(null);

		return connectionFactoryBean;
	}
}
