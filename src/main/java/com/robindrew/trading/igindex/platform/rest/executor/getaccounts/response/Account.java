package com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class Account extends IgJsonObject {

	private String accountId;
	private String accountName;
	private String accountAlias;
	private AccountStatus status;
	private AccountType accountType;
	private boolean preferred;
	private Balance balance;
	private CurrencyCode currency;
	private boolean canTransferFrom;
	private boolean canTransferTo;

	public String getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getAccountAlias() {
		return accountAlias;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public boolean isPreferred() {
		return preferred;
	}

	public Balance getBalance() {
		return balance;
	}

	public CurrencyCode getCurrency() {
		return currency;
	}

	public boolean isCanTransferFrom() {
		return canTransferFrom;
	}

	public boolean isCanTransferTo() {
		return canTransferTo;
	}
}
