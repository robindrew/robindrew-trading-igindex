package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.Market;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.MarketNavigation;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.Node;

public class GetMarketNavigationResponse extends IgJsonObject {

	private List<Node> nodes;
	private List<Market> markets;

	public MarketNavigation getMarketNavigation() {
		return new MarketNavigation(nodes, markets);
	}
}
