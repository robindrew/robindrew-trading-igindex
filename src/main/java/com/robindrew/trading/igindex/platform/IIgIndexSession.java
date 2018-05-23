package com.robindrew.trading.igindex.platform;

public interface IIgIndexSession {

	IgIndexCredentials getCredentials();

	IgIndexEnvironment getEnvironment();

	String getClientSecurityToken();

	String getAccountSecurityToken();

	String getLightstreamerEndpoint();

	boolean hasAccountSecurityToken();

	boolean hasClientSecurityToken();

	boolean hasLightstreamerEndpoint();

	void setAccountSecurityToken(String token);

	void setClientSecurityToken(String token);

	void setLightstreamerEndpoint(String endpoint);

}
