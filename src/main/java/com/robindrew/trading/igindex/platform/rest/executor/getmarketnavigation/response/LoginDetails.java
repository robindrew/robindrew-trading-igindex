package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class LoginDetails extends IgIndexJsonObject {

	private String accountType;
	private String currencyIsoCode;
	private String currencySymbol;
	private String currentAccountId;
	private String lightstreamerEndpoint;
	private String clientId;
	private String timezoneOffset;
	private String hasActiveDemoAccounts;
	private String hasActiveLiveAccounts;
	private String trailingStopsEnabled;
	private String dealingEnabled;

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
