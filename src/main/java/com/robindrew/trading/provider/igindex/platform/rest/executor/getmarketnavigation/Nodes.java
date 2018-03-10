package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Nodes extends IgJsonObject {

	private final List<Node> nodeList = new ArrayList<>();

	public Nodes(List<IJson> nodes) {
		for (IJson node : nodes) {
			int id = node.getInt("id");
			String name = node.get("name");
			nodeList.add(new Node(id, name));
		}
	}

	public int size() {
		return nodeList.size();
	}

	public List<Node> getNodeList() {
		return ImmutableList.copyOf(nodeList);
	}
}
