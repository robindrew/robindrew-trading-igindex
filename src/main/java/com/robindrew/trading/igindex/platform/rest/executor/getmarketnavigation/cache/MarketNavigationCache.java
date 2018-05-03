package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.cache;

import static java.util.concurrent.TimeUnit.MINUTES;

import com.robindrew.trading.igindex.platform.rest.cache.MemoryCache;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.MarketNavigation;

public class MarketNavigationCache extends MemoryCache<Integer, MarketNavigation> implements IMarketNavigationCache {

	public MarketNavigationCache() {
		super(10, MINUTES);
	}
}
