package com.robindrew.trading.igindex.platform.rest.executor.getpricelist;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class PriceCandle extends IgJsonObject {

	private final LocalDateTime snapshotTimeUTC;
	private final Price openPrice;
	private final Price closePrice;
	private final Price highPrice;
	private final Price lowPrice;
	private final BigDecimal lastTradedVolume;

	public PriceCandle(IJson object) {
		// snapshotTime
		snapshotTimeUTC = object.getLocalDateTime("snapshotTimeUTC");
		openPrice = new Price(object.getObject("openPrice"));
		closePrice = new Price(object.getObject("closePrice"));
		highPrice = new Price(object.getObject("highPrice"));
		lowPrice = new Price(object.getObject("lowPrice"));
		lastTradedVolume = object.getBigDecimal("lastTradedVolume", true);
	}

	public LocalDateTime getSnapshotTimeUTC() {
		return snapshotTimeUTC;
	}

	public Price getOpenPrice() {
		return openPrice;
	}

	public Price getClosePrice() {
		return closePrice;
	}

	public Price getHighPrice() {
		return highPrice;
	}

	public Price getLowPrice() {
		return lowPrice;
	}

	public BigDecimal getLastTradedVolume() {
		return lastTradedVolume;
	}

}
