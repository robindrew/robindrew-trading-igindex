package com.robindrew.trading.provider.igindex.platform;

import com.robindrew.common.util.Check;

public class IgSession implements IIgSession {

	private final IgCredentials credentials;
	private final IgEnvironment environment;

	private String clientSecurityToken = "";
	private String accountSecurityToken = "";
	private String lightstreamerEndpoint = "";

	public IgSession(IgCredentials credentials, IgEnvironment environment) {
		this.credentials = Check.notNull("credentials", credentials);
		this.environment = Check.notNull("environment", environment);
	}

	@Override
	public IgCredentials getCredentials() {
		return credentials;
	}

	@Override
	public IgEnvironment getEnvironment() {
		return environment;
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

	public String getLightstreamerEndpoint() {
		if (lightstreamerEndpoint.isEmpty()) {
			throw new IllegalStateException("lightstreamerEndpoint not set");
		}
		return lightstreamerEndpoint;
	}

	public void setClientSecurityToken(String token) {
		if (token.isEmpty()) {
			throw new IllegalArgumentException("token is empty");
		}
		this.clientSecurityToken = token;
	}

	public void setAccountSecurityToken(String token) {
		if (token.isEmpty()) {
			throw new IllegalArgumentException("token is empty");
		}
		this.accountSecurityToken = token;
	}

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

	public boolean hasAccountSecurityToken() {
		return !accountSecurityToken.isEmpty();
	}

	public boolean hasClientSecurityToken() {
		return !clientSecurityToken.isEmpty();
	}

}
