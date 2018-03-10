package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Markets extends IgJsonObject {

	private final List<Market> marketList;

	public Markets(List<IJson> nodes) {
		List<Market> list = new ArrayList<>();
		for (IJson node : nodes) {
			list.add(new Market(node));
		}
		marketList = ImmutableList.copyOf(list);
	}

	public List<Market> getMarketList() {
		return marketList;
	}

}
