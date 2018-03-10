package com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist;

import java.time.temporal.ChronoUnit;

import com.robindrew.common.date.UnitChrono;

public enum PriceResolution {

	SECOND, MINUTE, MINUTE_2, MINUTE_3, MINUTE_5, MINUTE_10, MINUTE_15, MINUTE_30, HOUR, HOUR_2, HOUR_3, HOUR_4, DAY, WEEK, MONTH;

	public static PriceResolution valueOf(UnitChrono unit) {
		return valueOf(unit.getUnit(), unit.getTime());
	}

	public static PriceResolution valueOf(ChronoUnit unit, long amount) {
		switch (unit) {
			case SECONDS:
				if (amount == 1) {
					return SECOND;
				}
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);

			case MINUTES:
				if (amount == 1) {
					return MINUTE;
				}
				if (amount == 2) {
					return MINUTE_2;
				}
				if (amount == 3) {
					return MINUTE_3;
				}
				if (amount == 5) {
					return MINUTE_5;
				}
				if (amount == 10) {
					return MINUTE_10;
				}
				if (amount == 15) {
					return MINUTE_15;
				}
				if (amount == 30) {
					return MINUTE_30;
				}
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);

			case HOURS:
				if (amount == 1) {
					return HOUR;
				}
				if (amount == 2) {
					return HOUR_2;
				}
				if (amount == 3) {
					return HOUR_3;
				}
				if (amount == 4) {
					return HOUR_4;
				}
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);

			case DAYS:
				if (amount == 1) {
					return DAY;
				}
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);

			case WEEKS:
				if (amount == 1) {
					return WEEK;
				}
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);

			case MONTHS:
				if (amount == 1) {
					return MONTH;
				}
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);

			default:
				throw new IllegalArgumentException("Unit not supported: " + unit + ", amount=" + amount);
		}
	}

}
