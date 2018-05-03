package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.cache;

import java.util.concurrent.Callable;

import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.MarketNavigation;

public interface IMarketNavigationCache {

	void remove(Integer id);

	MarketNavigation get(Integer id, Callable<? extends MarketNavigation> loader);

	MarketNavigation get(Integer id);

}
