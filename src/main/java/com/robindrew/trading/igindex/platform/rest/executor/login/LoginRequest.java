package com.robindrew.trading.igindex.platform.rest.executor.login;

public class LoginRequest {

	private final String identifier;
	private final String password;
	private final String encryptedPassword;

	public LoginRequest(String identifier, String password) {
		this.identifier = identifier;
		this.password = password;
		this.encryptedPassword = null;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getPassword() {
		return password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

}
