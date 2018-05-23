package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.Market;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.MarketNavigation;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.Node;

public class GetMarketNavigationResponse extends IgIndexJsonObject {

	private List<Node> nodes;
	private List<Market> markets;

	public MarketNavigation getMarketNavigation() {
		return new MarketNavigation(nodes, markets);
	}
}
