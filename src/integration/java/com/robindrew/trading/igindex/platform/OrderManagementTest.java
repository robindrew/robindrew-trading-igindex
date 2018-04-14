package com.robindrew.trading.igindex.platform;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.IgRestService;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.position.order.PositionOrder;
import com.robindrew.trading.trade.TradeDirection;

public class OrderManagementTest {

	@Test
	public void opneAndCloseOrder() {

		IgInstrument instrument = IgInstrument.SPOT_GBP_USD;

		String apiKey = "9d07a6a968efc5721f6787ff206d463d2676b095";
		String username = "robindrew2002";
		String password = "Lvigux123";

		IgCredentials credentials = new IgCredentials(apiKey, username, password);
		IgEnvironment environment = IgEnvironment.DEMO;
		IgSession session = new IgSession(credentials, environment);
		IIgRestService rest = new IgRestService(session);
		rest.login();

		IgTradingPlatform platform = new IgTradingPlatform(rest);
		IPositionService positions = platform.getPositionService();

		TradeDirection direction = TradeDirection.SELL;
		CurrencyCode tradeCurrency = CurrencyCode.GBP;
		BigDecimal tradeSize = new BigDecimal(1);
		int stopLossDistance = 100;
		int profitLimitDistance = 150;

		// Execute trade
		IPositionOrder order = new PositionOrder(instrument, direction, tradeCurrency, tradeSize, stopLossDistance, profitLimitDistance);
		IPosition position = positions.openPosition(order);

		Threads.sleep(5, TimeUnit.SECONDS);

		positions.closePosition(position);

	}

}
