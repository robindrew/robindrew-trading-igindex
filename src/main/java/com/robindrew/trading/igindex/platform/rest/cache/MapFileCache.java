package com.robindrew.trading.igindex.platform.rest.cache;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;

public class MapFileCache<K, V> {

	private final File directory;
	private final Class<V> valueType;
	private final Map<K, V> cache = new LinkedHashMap<>();

	protected MapFileCache(Class<V> valueType, File directory) {
		this.valueType = Check.notNull("valueType", valueType);
		this.directory = Check.existsDirectory("directory", directory);
	}

	protected File getDirectory() {
		return directory;
	}

	public void put(K key, V value) {
		Check.notNull("key", key);
		Check.notNull("value", value);

		synchronized (cache) {
			cache.put(key, value);

			File file = getFile(key);
			writeToFile(value, file);
		}
	}

	public V get(K key) {
		Check.notNull("key", key);

		synchronized (cache) {
			V value = cache.get(key);
			if (value == null) {
				File file = getFile(key);
				value = readFromFile(file);
			}
			return value;
		}
	}

	protected File getFile(K key) {
		return new File(getDirectory(), key + ".json");
	}

	protected void writeToFile(V value, File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(value);
		Files.writeFromString(file, json);
	}

	protected V readFromFile(File file) {
		if (!file.exists()) {
			return null;
		}
		String json = Files.readToString(file);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(json, valueType);
	}

}
