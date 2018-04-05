package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import java.util.concurrent.Callable;

public interface IMarketNavigationCache {

	void remove(Integer id);

	MarketNavigation get(Integer id, Callable<? extends MarketNavigation> loader);

	MarketNavigation get(Integer id);

}
