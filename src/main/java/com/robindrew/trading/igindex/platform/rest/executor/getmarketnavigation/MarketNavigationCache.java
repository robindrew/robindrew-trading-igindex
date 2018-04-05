package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import static java.util.concurrent.TimeUnit.MINUTES;

import com.robindrew.trading.igindex.platform.rest.cache.MemoryCache;

public class MarketNavigationCache extends MemoryCache<Integer, MarketNavigation> implements IMarketNavigationCache {

	public MarketNavigationCache() {
		super(10, MINUTES);
	}
}
