package com.robindrew.trading.igindex;

import static com.robindrew.trading.trade.window.TradingHours.openUntil;
import static java.time.DayOfWeek.FRIDAY;

import java.time.LocalTime;

import com.robindrew.trading.trade.window.TradingWindow;

public class IgFxTradingWindow extends TradingWindow {

	public IgFxTradingWindow() {
		closedAtWeekends();
		setOpeningHours(FRIDAY, openUntil(LocalTime.of(22, 0)));
	}
}
