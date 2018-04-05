package com.robindrew.trading.igindex.platform.rest.executor.getmarkets;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class DealingRules extends IgJsonObject {

	private final UnitValue minStepDistance;
	private final UnitValue minDealSize;
	private final UnitValue minControlledRiskStopDistance;
	private final UnitValue minNormalStopOrLimitDistance;
	private final UnitValue maxStopOrLimitDistance;
	private final MarketOrderPreference marketOrderPreference;
	private final Availability trailingStopsPreference;

	public DealingRules(IJson object) {
		minStepDistance = new UnitValue(object.getObject("minStepDistance"));
		minDealSize = new UnitValue(object.getObject("minDealSize"));
		minControlledRiskStopDistance = new UnitValue(object.getObject("minControlledRiskStopDistance"));
		minNormalStopOrLimitDistance = new UnitValue(object.getObject("minNormalStopOrLimitDistance"));
		maxStopOrLimitDistance = new UnitValue(object.getObject("maxStopOrLimitDistance"));
		marketOrderPreference = object.getEnum("marketOrderPreference", MarketOrderPreference.class);
		trailingStopsPreference = object.getEnum("trailingStopsPreference", Availability.class);
	}

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
