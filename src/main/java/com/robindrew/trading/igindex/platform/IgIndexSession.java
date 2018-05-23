package com.robindrew.trading.igindex.platform;

import com.robindrew.common.util.Check;

public class IgIndexSession implements IIgIndexSession {

	private final IgIndexCredentials credentials;
	private final IgIndexEnvironment environment;

	private volatile String clientSecurityToken = "";
	private volatile String accountSecurityToken = "";
	private volatile String lightstreamerEndpoint = "";

	public IgIndexSession(IgIndexCredentials credentials, IgIndexEnvironment environment) {
		this.credentials = Check.notNull("credentials", credentials);
		this.environment = Check.notNull("environment", environment);
	}

	@Override
	public IgIndexCredentials getCredentials() {
		return credentials;
	}

	@Override
	public IgIndexEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public boolean hasAccountSecurityToken() {
		return !accountSecurityToken.isEmpty();
	}

	@Override
	public boolean hasClientSecurityToken() {
		return !clientSecurityToken.isEmpty();
	}

	@Override
	public boolean hasLightstreamerEndpoint() {
		return !lightstreamerEndpoint.isEmpty();
	}

	@Override
	public String getClientSecurityToken() {
		if (clientSecurityToken.isEmpty()) {
			throw new IllegalStateException("clientSecurityToken not set");
		}
		return clientSecurityToken;
	}

	@Override
	public String getAccountSecurityToken() {
		if (accountSecurityToken.isEmpty()) {
			throw new IllegalStateException("accountSecurityToken not set");
		}
		return accountSecurityToken;
	}

	@Override
	public String getLightstreamerEndpoint() {
		if (lightstreamerEndpoint.isEmpty()) {
			throw new IllegalStateException("lightstreamerEndpoint not set");
		}
		return lightstreamerEndpoint;
	}

	@Override
	public void setClientSecurityToken(String token) {
		if (token.isEmpty()) {
			throw new IllegalArgumentException("token is empty");
		}
		this.clientSecurityToken = token;
	}

	@Override
	public void setAccountSecurityToken(String token) {
		if (token.isEmpty()) {
			throw new IllegalArgumentException("token is empty");
		}
		this.accountSecurityToken = token;
	}

	@Override
	public void setLightstreamerEndpoint(String endpoint) {
		if (endpoint.isEmpty()) {
			throw new IllegalArgumentException("token is empty");
		}
		this.lightstreamerEndpoint = endpoint;
	}

	@Override
	public String toString() {
		return credentials + "/" + environment;
	}
}
