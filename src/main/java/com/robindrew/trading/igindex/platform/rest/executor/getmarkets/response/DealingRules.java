package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class DealingRules extends IgJsonObject {

	private UnitValue minStepDistance;
	private UnitValue minDealSize;
	private UnitValue minControlledRiskStopDistance;
	private UnitValue minNormalStopOrLimitDistance;
	private UnitValue maxStopOrLimitDistance;
	private MarketOrderPreference marketOrderPreference;
	private Availability trailingStopsPreference;

	public UnitValue getMinStepDistance() {
		return minStepDistance;
	}

	public UnitValue getMinDealSize() {
		return minDealSize;
	}

	public UnitValue getMinControlledRiskStopDistance() {
		return minControlledRiskStopDistance;
	}

	public UnitValue getMinNormalStopOrLimitDistance() {
		return minNormalStopOrLimitDistance;
	}

	public UnitValue getMaxStopOrLimitDistance() {
		return maxStopOrLimitDistance;
	}

	public MarketOrderPreference getMarketOrderPreference() {
		return marketOrderPreference;
	}

	public Availability getTrailingStopsPreference() {
		return trailingStopsPreference;
	}

}
