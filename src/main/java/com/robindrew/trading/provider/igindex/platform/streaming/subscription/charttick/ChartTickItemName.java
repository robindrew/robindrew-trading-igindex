package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

public class ChartTickItemName {

	private static final String PREFIX = "CHART:";
	private static final String POSTFIX = ":TICK";

	private final String itemName;

	public ChartTickItemName(String itemName) {
		if (!itemName.startsWith(PREFIX)) {
			throw new IllegalArgumentException("itemName: '" + itemName + "'");
		}
		if (!itemName.endsWith(POSTFIX)) {
			throw new IllegalArgumentException("itemName: '" + itemName + "'");
		}
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

}
