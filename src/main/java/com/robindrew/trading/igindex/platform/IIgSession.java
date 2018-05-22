package com.robindrew.trading.igindex.platform;

public interface IIgSession {

	IgCredentials getCredentials();

	IgEnvironment getEnvironment();

	String getClientSecurityToken();

	String getAccountSecurityToken();

	String getLightstreamerEndpoint();

}
