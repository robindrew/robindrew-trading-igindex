package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class Markets extends IgIndexJsonObject {

	private Instrument instrument;
	private DealingRules dealingRules;
	private Snapshot snapshot;

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
