package com.robindrew.trading.provider.igindex.platform.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.provider.igindex.IgInstrument;
import com.robindrew.trading.provider.igindex.platform.IgSession;
import com.robindrew.trading.provider.igindex.platform.rest.executor.closeposition.ClosePositionExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.closeposition.ClosePositionRequest;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.Account;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.AccountType;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.GetAccountsExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.GetAccountsResponse;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity.Activity;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity.ActivityCache;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity.GetActivityExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.GetMarketNavigationExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.IMarketNavigationCache;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.MarketNavigation;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.MarketNavigationCache;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets.GetMarketsExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets.Markets;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets.MarketsCache;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions.GetPositionsExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions.GetPositionsResponse;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions.MarketPosition;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist.GetPriceListExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist.GetPriceListRequest;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist.GetPriceListResponse;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist.PriceList;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist.PriceResolution;
import com.robindrew.trading.provider.igindex.platform.rest.executor.login.LoginDetails;
import com.robindrew.trading.provider.igindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.provider.igindex.platform.rest.executor.logout.LogoutExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.openposition.OpenPositionExecutor;
import com.robindrew.trading.provider.igindex.platform.rest.executor.openposition.OpenPositionRequest;
import com.robindrew.trading.provider.igindex.platform.rest.executor.openposition.OpenPositionResponse;
import com.robindrew.trading.provider.igindex.platform.rest.executor.searchMarkets.SearchMarketsExecutor;
import com.robindrew.trading.provider.igindex.platform.streaming.IgStreamingService;
import com.robindrew.trading.trade.TradeDirection;

public class IgRestService implements IIgRestService {

	private final IgSession session;
	private final MarketsCache marketsCache;
	private final IMarketNavigationCache marketNavigationCache;
	private final ActivityCache activityCache;
	private final IStreamingService streamingService;

	public IgRestService(IgSession session, IMarketNavigationCache marketNavigationCache) {
		if (session == null) {
			throw new NullPointerException("session");
		}
		this.session = session;
		this.marketsCache = new MarketsCache();
		this.marketNavigationCache = marketNavigationCache;
		this.activityCache = new ActivityCache();
		this.streamingService = new IgStreamingService(session);
	}

	public IgRestService(IgSession session) {
		this(session, new MarketNavigationCache());
	}

	@Override
	public IgSession getSession() {
		return session;
	}

	@Override
	public IStreamingService getStreamingService() {
		return streamingService;
	}

	@Override
	public synchronized LoginDetails login() {
		LoginResponse response = new LoginExecutor(this).execute();
		return response.getDetails();
	}

	@Override
	public synchronized void logout() {
		new LogoutExecutor(this).execute();
	}

	@Override
	public List<MarketPosition> getPositionList() {
		GetPositionsResponse response = new GetPositionsExecutor(this).execute();
		return response.getMarketPositions();
	}

	@Override
	public synchronized void closePosition(IPosition position) {
		activityCache.clear();

		ClosePositionRequest request = ClosePositionRequest.forPosition(position);
		new ClosePositionExecutor(this, request).execute();
	}

	@Override
	public List<Account> getAccountList() {
		GetAccountsResponse response = new GetAccountsExecutor(this).execute();
		return response.getAccounts();
	}

	@Override
	public synchronized void closeAllPositions() {
		activityCache.clear();

		// List open positions
		List<MarketPosition> positions = getPositionList();

		// Close all open positions!
		for (MarketPosition position : positions) {
			closePosition(position);
		}
	}

	@Override
	public Account getAccount(AccountType type) {
		for (Account account : getAccountList()) {
			if (account.getAccountType().equals(type)) {
				return account;
			}
		}
		throw new IllegalArgumentException("Account not found with type: " + type);
	}

	@Override
	public Markets getMarkets(String epic, boolean latest) {
		if (latest) {
			marketsCache.remove(epic);
		}
		return marketsCache.get(epic, () -> new GetMarketsExecutor(IgRestService.this, epic).execute().getMarkets());
	}

	@Override
	public PriceList getPriceList(IgInstrument instrument, UnitChrono unit, int size) {
		if (size < 1) {
			throw new IllegalArgumentException("size=" + size);
		}

		PriceResolution resolution = PriceResolution.valueOf(unit);
		GetPriceListRequest request = new GetPriceListRequest(instrument, resolution);
		request.setMax(size);

		GetPriceListResponse response = new GetPriceListExecutor(this, request).execute();
		return response.getPriceList();
	}

	@Override
	public PriceList getPriceList(IgInstrument instrument, UnitChrono unit, LocalDateTime from, LocalDateTime to) {

		PriceResolution resolution = PriceResolution.valueOf(unit);
		GetPriceListRequest request = new GetPriceListRequest(instrument, resolution);
		request.setFrom(from);
		request.setTo(to);

		GetPriceListResponse response = new GetPriceListExecutor(this, request).execute();
		return response.getPriceList();
	}

	@Override
	public MarketPosition getPositionByDealId(String dealId) {
		for (MarketPosition position : getPositionList()) {
			if (position.getPosition().getDealId().equals(dealId)) {
				return position;
			}
		}
		throw new IllegalArgumentException("dealId=" + dealId);
	}

	@Override
	public MarketNavigation getMarketNavigation(int id, boolean latest) {
		if (latest) {
			marketNavigationCache.remove(id);
		}
		return marketNavigationCache.get(id, () -> new GetMarketNavigationExecutor(IgRestService.this, id).execute().getMarketNavigation());
	}

	@Override
	public List<Activity> getActivityList(boolean latest) {
		if (latest) {
			activityCache.clear();
		}
		// Currently limiting to the past 3 months ...
		LocalDate date = LocalDate.now().minus(Period.ofMonths(3));
		return activityCache.get(date, () -> new GetActivityExecutor(IgRestService.this, date).execute().getActivities());
	}

	@Override
	public com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation.Markets searchMarkets(String text) {
		return new SearchMarketsExecutor(this, text).execute().getMarkets();
	}

	@Override
	public String openPosition(String epic, TradeDirection direction, BigDecimal size, int stopLoss, Integer stopProfit) {
		activityCache.clear();
		OpenPositionRequest request = new OpenPositionRequest(epic, direction, size, stopLoss, stopProfit);
		OpenPositionResponse response = new OpenPositionExecutor(this, request).execute();
		return response.getDealReference();
	}

}
