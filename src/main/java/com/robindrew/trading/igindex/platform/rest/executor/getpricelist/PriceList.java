package com.robindrew.trading.igindex.platform.rest.executor.getpricelist;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.InstrumentType;

public class PriceList extends IgJsonObject implements Iterable<PriceCandle> {

	private final List<PriceCandle> prices;
	private final InstrumentType instrumentType;
	private final Metadata metadata;

	public PriceList(IJson object) {
		prices = object.getList("prices", element -> new PriceCandle(element));
		instrumentType = object.getEnum("instrumentType", InstrumentType.class);
		metadata = new Metadata(object.getObject("metadata"));
	}

	public List<PriceCandle> getPrices() {
		return ImmutableList.copyOf(prices);
	}

	public InstrumentType getInstrumentType() {
		return instrumentType;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	@Override
	public Iterator<PriceCandle> iterator() {
		return getPrices().iterator();
	}
}
