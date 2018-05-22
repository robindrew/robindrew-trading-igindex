package com.robindrew.trading.igindex.platform.streaming.lightstreamer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.platform.IIgSession;

public class ConnectionInfoBuilder {

	private static final Logger log = LoggerFactory.getLogger(ConnectionInfoBuilder.class);

	private final IIgSession session;

	public ConnectionInfoBuilder(IIgSession session) {
		this.session = Check.notNull("session", session);
	}

	public ConnectionInfo build() {

		ConnectionInfo info = new ConnectionInfo();
		info.user = session.getCredentials().getUsername();
		info.password = "CST-" + session.getClientSecurityToken() + "|XST-" + session.getAccountSecurityToken();
		info.pushServerUrl = session.getLightstreamerEndpoint();

		log.info("Username: {}", info.user);
		log.info("Server: {}", info.pushServerUrl);
		return info;
	}

}
