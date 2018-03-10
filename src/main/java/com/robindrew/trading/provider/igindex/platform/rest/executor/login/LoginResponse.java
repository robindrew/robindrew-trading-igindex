package com.robindrew.trading.provider.igindex.platform.rest.executor.login;

import com.robindrew.common.json.IJson;

public class LoginResponse {

	private final LoginDetails details;

	public LoginResponse(IJson object) {
		this.details = new LoginDetails(object);
	}

	public LoginDetails getDetails() {
		return details;
	}

}
