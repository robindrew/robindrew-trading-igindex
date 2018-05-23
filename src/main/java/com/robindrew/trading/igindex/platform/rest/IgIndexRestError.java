package com.robindrew.trading.igindex.platform.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.robindrew.common.json.IJson;

public class IgIndexRestError {

	private static final Logger log = LoggerFactory.getLogger(IgIndexRestError.class);

	public static Optional<IgIndexRestError> getRestError(IJson json) {
		String errorCode = json.get("errorCode", true);
		if (errorCode == null) {
			return Optional.absent();
		}
		return Optional.of(new IgIndexRestError(errorCode));
	}

	private final String errorCode;

	public IgIndexRestError(String errorCode) {
		this.errorCode = errorCode;

		log.warn("[Error Code] " + errorCode);
	}

	public boolean hasValue(String code) {
		return errorCode.equals(code);
	}

	public boolean isLoginInvalid() {
		return isSecurityClientTokenInvalid() || isSecurityClientTokenMissing() || isSecurityAccountTokenInvalid() || isSecurityAccountTokenMissing();
	}

	public boolean isSecurityClientTokenInvalid() {
		return hasValue("error.security.client-token-invalid");
	}

	public boolean isSecurityClientTokenMissing() {
		return hasValue("error.security.client-token-missing");
	}

	public boolean isSecurityAccountTokenInvalid() {
		return hasValue("error.security.account-token-invalid");
	}

	public boolean isSecurityAccountTokenMissing() {
		return hasValue("error.security.account-token-missing");
	}

	@Override
	public String toString() {
		return errorCode;
	}

}
