package com.robindrew.trading.igindex.platform.rest.executor.login;

import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.login.response.Account;
import com.robindrew.trading.igindex.platform.rest.executor.login.response.AccountInfo;

public class LoginResponse extends IgJsonObject {

	private String accountType;
	private AccountInfo accountInfo;
	private String currencyIsoCode;
	private String currencySymbol;
	private String currentAccountId;
	private String lightstreamerEndpoint;
	private List<Account> accounts;
	private String clientId;
	private int timezoneOffset;
	private boolean hasActiveDemoAccounts;
	private boolean hasActiveLiveAccounts;
	private boolean trailingStopsEnabled;
	private String reroutingEnvironment;
	private boolean dealingEnabled;

	public String getAccountType() {
		return accountType;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
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

	public List<Account> getAccounts() {
		return accounts;
	}

	public String getClientId() {
		return clientId;
	}

	public int getTimezoneOffset() {
		return timezoneOffset;
	}

	public boolean isHasActiveDemoAccounts() {
		return hasActiveDemoAccounts;
	}

	public boolean isHasActiveLiveAccounts() {
		return hasActiveLiveAccounts;
	}

	public boolean isTrailingStopsEnabled() {
		return trailingStopsEnabled;
	}

	public String getReroutingEnvironment() {
		return reroutingEnvironment;
	}

	public boolean isDealingEnabled() {
		return dealingEnabled;
	}

}
