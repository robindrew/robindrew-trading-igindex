package com.robindrew.trading.igindex.platform.rest.executor.getpricelist;

public class QueryBuilder {

	private final StringBuilder query = new StringBuilder();
	private int count = 0;

	public QueryBuilder append(String key, String value) {
		if (key.isEmpty()) {
			throw new IllegalArgumentException("key is empty");
		}
		if (value == null) {
			throw new NullPointerException("value");
		}

		if (count == 0) {
			query.append('?');
		} else {
			query.append('&');
		}

		query.append(key);
		query.append('=');
		query.append(value);

		count++;
		return this;
	}

	public QueryBuilder append(String key, Object value) {
		if (value == null) {
			throw new NullPointerException("value");
		}
		return append(key, String.valueOf(value));
	}

	public QueryBuilder append(String key, int value) {
		return append(key, String.valueOf(value));
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
