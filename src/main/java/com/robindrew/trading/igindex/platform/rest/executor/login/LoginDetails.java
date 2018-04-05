package com.robindrew.trading.igindex.platform.rest.executor.login;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class LoginDetails extends IgJsonObject {

	private final String accountType;
	private final String currencyIsoCode;
	private final String currencySymbol;
	private final String currentAccountId;
	private final String lightstreamerEndpoint;
	private final String clientId;
	private final String timezoneOffset;
	private final String hasActiveDemoAccounts;
	private final String hasActiveLiveAccounts;
	private final String trailingStopsEnabled;
	private final String dealingEnabled;

	public LoginDetails(IJson object) {
		accountType = object.get("accountType");
		currencyIsoCode = object.get("currencyIsoCode");
		currencySymbol = object.get("currencySymbol");
		currentAccountId = object.get("currentAccountId");
		lightstreamerEndpoint = object.get("lightstreamerEndpoint");
		clientId = object.get("clientId");
		timezoneOffset = object.get("timezoneOffset");
		hasActiveDemoAccounts = object.get("hasActiveDemoAccounts");
		hasActiveLiveAccounts = object.get("hasActiveLiveAccounts");
		trailingStopsEnabled = object.get("trailingStopsEnabled");
		dealingEnabled = object.get("dealingEnabled");
	}

	public String getAccountType() {
		return accountType;
	}

	public String getCurrencyIsoCode() {
		return currencyIsoCode;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public String getCurrentAccountId() {
		return currentAccountId;
	}

	public String getLightstreamerEndpoint() {
		return lightstreamerEndpoint;
	}

	public String getClientId() {
		return clientId;
	}

	public String getTimezoneOffset() {
		return timezoneOffset;
	}

	public String getHasActiveDemoAccounts() {
		return hasActiveDemoAccounts;
	}

	public String getHasActiveLiveAccounts() {
		return hasActiveLiveAccounts;
	}

	public String getTrailingStopsEnabled() {
		return trailingStopsEnabled;
	}

	public String getDealingEnabled() {
		return dealingEnabled;
	}
}
