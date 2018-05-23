package com.robindrew.trading.igindex.platform.account;

import static com.robindrew.trading.provider.TradingProvider.IGINDEX;

import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.Account;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.AccountType;
import com.robindrew.trading.platform.account.AbstractAccountService;
import com.robindrew.trading.trade.cash.Cash;
import com.robindrew.trading.trade.cash.ICash;

public class IgIndexAccountService extends AbstractAccountService {

	private final AccountType type;
	private final IIgIndexRestService rest;

	public IgIndexAccountService(IIgIndexRestService rest, AccountType type) {
		super(IGINDEX);
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
