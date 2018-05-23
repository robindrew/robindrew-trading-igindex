package com.robindrew.trading.igindex.platform.rest.executor.getaccounts;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.Account;

public class GetAccountsResponse extends IgIndexJsonObject {

	private final List<Account> accounts = new ArrayList<>();

	public List<Account> toList() {
		return ImmutableList.copyOf(accounts);
	}

}
