package com.robindrew.trading.igindex.platform.account;

import static com.robindrew.trading.provider.TradingProvider.IGINDEX;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.Account;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.AccountType;
import com.robindrew.trading.platform.account.AbstractAccountService;
import com.robindrew.trading.trade.money.IMoney;
import com.robindrew.trading.trade.money.Money;

public class IgIndexAccountService extends AbstractAccountService {

	private final AccountType type;
	private final IIgIndexRestService rest;
	private final CurrencyCode currency;

	public IgIndexAccountService(IIgIndexRestService rest, AccountType type, CurrencyCode currency) {
		super(IGINDEX);
		this.rest = Check.notNull("rest", rest);
		this.type = Check.notNull("type", type);
		this.currency = Check.notNull("currency", currency);
	}

	@Override
	public String getAccountId() {
		Account account = rest.getAccount(type);
		return account.getAccountId();
	}

	@Override
	public IMoney getBalance() {
		Account account = rest.getAccount(type);
		return new Money(account.getBalance().getAvailable(), currency);
	}

}
