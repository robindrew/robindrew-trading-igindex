package com.robindrew.trading.igindex.platform.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.igindex.IgIndexInstrument;
import com.robindrew.trading.igindex.platform.IIgIndexSession;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.Account;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.AccountType;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.response.ActivityList;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.Market;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.MarketNavigation;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response.Markets;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.MarketPosition;
import com.robindrew.trading.igindex.platform.rest.executor.getpricelist.PriceList;
import com.robindrew.trading.igindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.trade.TradeDirection;

public interface IIgIndexRestService {

	ITransactionLog getTransactionLog();

	IIgIndexSession getSession();

	LoginResponse login();

	void logout();

	PriceList getPriceList(IgIndexInstrument instrument, UnitChrono unit, int size);

	PriceList getPriceList(IgIndexInstrument instrument, UnitChrono unit, LocalDateTime from, LocalDateTime to);

	List<Account> getAccountList();

	Account getAccount(AccountType type);

	String openPosition(String epic, TradeDirection direction, BigDecimal size, BigDecimal stopLoss, BigDecimal stopProfit);

	String closePosition(IPosition position);

	Set<String> closeAllPositions();

	Markets getMarkets(String epic, boolean latest);

	List<MarketPosition> getPositionList();

	MarketPosition getPositionByDealId(String dealId);

	MarketNavigation getMarketNavigation(int id, boolean latest);

	List<Market> searchMarkets(String text);

	ActivityList getActivityList(boolean latest);

	ActivityList getActivityList(LocalDateTime from);
}
