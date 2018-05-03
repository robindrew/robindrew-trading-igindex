package com.robindrew.trading.igindex.platform.rest.executor.login.response;

public class Account {

	private String accountId;
	private String accountName;
	private boolean preferred;
	private String accountType;

	public String getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public boolean isPreferred() {
		return preferred;
	}

	public String getAccountType() {
		return accountType;
	}

}
