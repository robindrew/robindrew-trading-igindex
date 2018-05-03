package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response;

import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class MarketNavigation extends IgJsonObject {

	private final List<Node> nodes;
	private final List<Market> markets;

	private volatile int id = 0;
	private volatile String name = "Unknown";
	private volatile int parentId = 0;

	public MarketNavigation(List<Node> nodes, List<Market> markets) {
		this.nodes = nodes;
		this.markets = markets;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Market> getMarkets() {
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
