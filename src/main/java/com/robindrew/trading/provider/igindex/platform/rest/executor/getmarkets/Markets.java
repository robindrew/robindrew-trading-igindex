package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Markets extends IgJsonObject {

	private final Instrument instrument;
	private final DealingRules dealingRules;
	private final Snapshot snapshot;

	public Markets(IJson object) {
		instrument = new Instrument(object.getObject("instrument"));
		dealingRules = new DealingRules(object.getObject("dealingRules"));
		snapshot = new Snapshot(object.getObject("snapshot"));
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public DealingRules getDealingRules() {
		return dealingRules;
	}

	public Snapshot getSnapshot() {
		return snapshot;
	}

}
