package com.robindrew.trading.igindex.platform;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.igindex.IgIndexInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.IgIndexRestService;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.log.StubTransactionLog;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.position.order.PositionOrder;
import com.robindrew.trading.trade.TradeDirection;

public class OrderManagementTest {

	@Test
	public void opneAndCloseOrder() {

		IgIndexInstrument instrument = IgIndexInstrument.SPOT_GBP_USD;

		String apiKey = System.getProperty("apiKey");
		String username = System.getProperty("username");
		String password = System.getProperty("password");

		IgIndexCredentials credentials = new IgIndexCredentials(apiKey, username, password);
		IgIndexEnvironment environment = IgIndexEnvironment.DEMO;
		IgIndexSession session = new IgIndexSession(credentials, environment);
		ITransactionLog log = new StubTransactionLog();
		IIgIndexRestService rest = new IgIndexRestService(session, log);
		rest.login();

		IgIndexTradingPlatform platform = new IgIndexTradingPlatform(rest);
		IPositionService positions = platform.getPositionService();

		TradeDirection direction = TradeDirection.SELL;
		CurrencyCode tradeCurrency = CurrencyCode.GBP;
		BigDecimal tradeSize = new BigDecimal("0.5");
		int stopLossDistance = 100;
		int profitLimitDistance = 150;

		// Execute trade
		IPositionOrder order = new PositionOrder(instrument, direction, tradeCurrency, tradeSize, stopLossDistance, profitLimitDistance);
		IPosition position = positions.openPosition(order);

		Threads.sleep(5, TimeUnit.SECONDS);

		positions.closePosition(position);

	}

}
