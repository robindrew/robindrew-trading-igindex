package com.robindrew.trading.provider.igindex.platform.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.provider.igindex.IgInstrument;
import com.robindrew.trading.provider.igindex.platform.IgSession;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.Account;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.AccountType;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity.ActivityList;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.MarketNavigation;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets.Markets;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions.MarketPosition;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist.PriceList;
import com.robindrew.trading.provider.igindex.platform.rest.executor.login.LoginDetails;
import com.robindrew.trading.trade.TradeDirection;

public interface IIgRestService {

	IgSession getSession();

	LoginDetails login();

	void logout();

	PriceList getPriceList(IgInstrument instrument, UnitChrono unit, int size);

	PriceList getPriceList(IgInstrument instrument, UnitChrono unit, LocalDateTime from, LocalDateTime to);

	List<Account> getAccountList();

	Account getAccount(AccountType type);

	String openPosition(String epic, TradeDirection direction, BigDecimal size, int stopLoss, Integer stopProfit);

	String closePosition(IPosition position);

	Set<String> closeAllPositions();

	IStreamingService getStreamingService();

	Markets getMarkets(String epic, boolean latest);

	List<MarketPosition> getPositionList();

	MarketPosition getPositionByDealId(String dealId);

	MarketNavigation getMarketNavigation(int id, boolean latest);

	com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.Markets searchMarkets(String text);

	ActivityList getActivityList(boolean latest);

	ActivityList getActivityList(LocalDateTime from);
}
