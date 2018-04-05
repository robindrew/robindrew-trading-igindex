package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class Node extends IgJsonObject {

	private final int id;
	private final String name;

	public Node(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
