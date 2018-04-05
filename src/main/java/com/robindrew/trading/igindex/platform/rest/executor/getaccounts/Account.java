package com.robindrew.trading.igindex.platform.rest.executor.getaccounts;

import com.robindrew.common.json.IJson;
import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class Account extends IgJsonObject {

	private final String accountId;
	private final String accountName;
	private final String accountAlias;
	private final AccountStatus status;
	private final AccountType accountType;
	private final boolean preferred;
	private final Balance balance;
	private final CurrencyCode currency;
	private final boolean canTransferFrom;
	private final boolean canTransferTo;

	public Account(IJson object) {
		accountId = object.get("accountId");
		accountName = object.get("accountName");
		accountAlias = object.get("accountAlias", true);
		status = object.getEnum("status", AccountStatus.class);
		accountType = object.getEnum("accountType", AccountType.class);
		preferred = object.getBoolean("preferred");
		balance = object.contains("balance") ? new Balance(object.getObject("balance")) : null;
		currency = object.getEnum("currency", CurrencyCode.class);
		canTransferFrom = object.getBoolean("canTransferFrom");
		canTransferTo = object.getBoolean("canTransferTo");
	}

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
