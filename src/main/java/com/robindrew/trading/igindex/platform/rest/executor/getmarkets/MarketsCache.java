package com.robindrew.trading.igindex.platform.rest.executor.getmarkets;

import static java.util.concurrent.TimeUnit.MINUTES;

import com.robindrew.trading.igindex.platform.rest.cache.MemoryCache;

public class MarketsCache extends MemoryCache<String, Markets> {

	public MarketsCache() {
		super(10, MINUTES);
	}
}
