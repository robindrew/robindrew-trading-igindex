package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class MarketNavigation extends IgJsonObject {

	private final Nodes nodes;
	private final Markets markets;

	private volatile int id = 0;
	private volatile String name = "Unknown";
	private volatile int parentId = 0;

	public MarketNavigation(Nodes nodes, Markets markets) {
		this.nodes = nodes;
		this.markets = markets;
	}

	public Nodes getNodes() {
		return nodes;
	}

	public Markets getMarkets() {
		return markets;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
