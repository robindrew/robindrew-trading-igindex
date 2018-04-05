package com.robindrew.trading.igindex.platform.streaming.subscription;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class FieldSet {

	private final Set<String> fieldSet = new LinkedHashSet<>();

	public FieldSet(String... fields) {
		this(Arrays.asList(fields));
	}

	public FieldSet(Collection<? extends String> fields) {
		if (fields.isEmpty()) {
			throw new IllegalArgumentException("fields is empty");
		}
		for (String field : fields) {
			fieldSet.add(field);
		}
	}

	public FieldSet add(String field) {
		fieldSet.add(field);
		return this;
	}

	public String[] toArray() {
		String[] fieldArray = new String[fieldSet.size()];
		return fieldSet.toArray(fieldArray);
	}

}
