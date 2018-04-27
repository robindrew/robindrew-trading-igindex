package com.robindrew.trading.igindex.platform.account;

import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.Account;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.AccountType;
import com.robindrew.trading.platform.account.IAccountService;
import com.robindrew.trading.trade.cash.Cash;
import com.robindrew.trading.trade.cash.ICash;

public class IgAccountService implements IAccountService {

	private final AccountType type;
	private final IIgRestService rest;

	public IgAccountService(IIgRestService rest, AccountType type) {
		this.rest = Check.notNull("rest", rest);
		this.type = Check.notNull("type", type);
	}

	@Override
	public String getAccountId() {
		Account account = rest.getAccount(type);
		return account.getAccountId();
	}

	@Override
	public ICash getBalance() {
		Account account = rest.getAccount(type);
		return new Cash(account.getBalance().getAvailable(), false);
	}

}
