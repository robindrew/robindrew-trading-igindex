package com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.time.LocalDate;
import java.util.List;

import com.robindrew.trading.provider.igindex.platform.rest.cache.MemoryCache;

public class ActivityCache extends MemoryCache<LocalDate, List<Activity>> {

	public ActivityCache() {
		super(10, MINUTES);
	}
}
