package com.robindrew.trading.igindex.platform.rest.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class MemoryCache<K, V> {

	private final Cache<K, V> cache;

	public MemoryCache(long expiryDuration, TimeUnit expiryUnit) {
		this(CacheBuilder.newBuilder().concurrencyLevel(10).expireAfterWrite(expiryDuration, expiryUnit).build());
	}

	public MemoryCache(Cache<K, V> cache) {
		this.cache = Check.notNull("cache", cache);
	}

	public V get(K key, Callable<? extends V> loader) {
		try {
			return cache.get(key, loader);
		} catch (ExecutionException e) {
			throw Java.propagate(e);
		}
	}

	public V get(K key) {
		return cache.getIfPresent(key);
	}

	public void remove(K date) {
		cache.invalidate(date);
	}

	public void clear() {
		cache.invalidateAll();
	}

}
