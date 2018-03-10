package com.robindrew.trading.provider.igindex.platform.rest;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.json.IJson;

public class IgRestError {

	private static final Logger log = LoggerFactory.getLogger(IgRestError.class);

	public static Optional<IgRestError> getRestError(IJson object) {
		String errorCode = object.get("errorCode", true);
		if (errorCode == null) {
			return empty();
		}
		return of(new IgRestError(errorCode));
	}

	private final String errorCode;

	public IgRestError(String errorCode) {
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
