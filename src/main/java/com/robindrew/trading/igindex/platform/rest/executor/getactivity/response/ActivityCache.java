package com.robindrew.trading.igindex.platform.rest.executor.getactivity.response;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.time.LocalDate;

import com.robindrew.trading.igindex.platform.rest.cache.MemoryCache;

public class ActivityCache extends MemoryCache<LocalDate, ActivityList> {

	public ActivityCache() {
		super(10, MINUTES);
	}
}
