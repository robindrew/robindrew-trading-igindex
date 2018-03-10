package com.robindrew.trading.provider.igindex.platform;

public interface IIgSession {

	IgCredentials getCredentials();

	IgEnvironment getEnvironment();

	String getClientSecurityToken();

	String getAccountSecurityToken();

}
