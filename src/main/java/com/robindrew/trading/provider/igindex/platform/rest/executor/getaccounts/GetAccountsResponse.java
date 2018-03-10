package com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class GetAccountsResponse extends IgJsonObject {

	private final List<Account> accounts = new ArrayList<>();

	public GetAccountsResponse(IJson object) {
		for (IJson element : object.getObjectList("accounts")) {
			accounts.add(new Account(element));
		}
	}

	public List<Account> getAccounts() {
		return ImmutableList.copyOf(accounts);
	}

}
