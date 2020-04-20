package com.robindrew.trading.igindex.platform.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.Dates;
import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.igindex.IgIndexInstrument;
import com.robindrew.trading.igindex.platform.IIgIndexSession;
import com.robindrew.trading.igindex.platform.rest.executor.closeposition.ClosePositionExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.closeposition.ClosePositionRequest;
import com.robindrew.trading.igindex.platform.rest.executor.closeposition.ClosePositionResponse;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.GetAccountsExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.GetAccountsResponse;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.Account;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.AccountType;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.GetActivityExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.response.ActivityCache;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.response.ActivityList;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.GetMarketNavigationExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.cache.IMarketNavigationCache;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.cache.MarketNavigationCache;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.Market;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response.MarketNavigation;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.GetMarketsExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response.Markets;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response.MarketsCache;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.GetPositionsExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.GetPositionsResponse;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.MarketPosition;
import com.robindrew.trading.igindex.platform.rest.executor.getpricelist.GetPriceListExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.getpricelist.GetPriceListRequest;
import com.robindrew.trading.igindex.platform.rest.executor.getpricelist.GetPriceListResponse;
import com.robindrew.trading.igindex.platform.rest.executor.getpricelist.PriceList;
import com.robindrew.trading.igindex.platform.rest.executor.getpricelist.PriceResolution;
import com.robindrew.trading.igindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.igindex.platform.rest.executor.logout.LogoutExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.openposition.OpenPositionExecutor;
import com.robindrew.trading.igindex.platform.rest.executor.openposition.OpenPositionRequest;
import com.robindrew.trading.igindex.platform.rest.executor.openposition.OpenPositionResponse;
import com.robindrew.trading.igindex.platform.rest.executor.searchMarkets.SearchMarketsExecutor;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.trade.TradeDirection;

public class IgIndexRestService implements IIgIndexRestService {

	private static final Logger log = LoggerFactory.getLogger(IgIndexRestService.class);

	private final IIgIndexSession session;
	private final MarketsCache marketsCache;
	private final IMarketNavigationCache marketNavigationCache;
	private final ActivityCache activityCache;
	private final ITransactionLog transactionLog;

	public IgIndexRestService(IIgIndexSession session, ITransactionLog transactionLog) {
		if (session == null) {
			throw new NullPointerException("session");
		}
		if (transactionLog == null) {
			throw new NullPointerException("transactionLog");
		}
		this.session = session;
		this.transactionLog = transactionLog;
		this.marketsCache = new MarketsCache();
		this.marketNavigationCache = new MarketNavigationCache();
		this.activityCache = new ActivityCache();
	}

	@Override
	public IIgIndexSession getSession() {
		return session;
	}

	public IMarketNavigationCache getMarketNavigationCache() {
		return marketNavigationCache;
	}

	@Override
	public ITransactionLog getTransactionLog() {
		return transactionLog;
	}

	@Override
	public synchronized LoginResponse login() {
		return new LoginExecutor(this).execute();
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
	public synchronized String closePosition(IPosition position) {
		activityCache.clear();

		ClosePositionRequest request = ClosePositionRequest.forPosition(position);
		ClosePositionResponse response = new ClosePositionExecutor(this, request).execute();
		String dealReference = response.getDealReference();

		// Sanity check (we expect the deal reference to be the same for the closed position)
		if (position instanceof MarketPosition) {
			MarketPosition market = (MarketPosition) position;
			String expected = market.getPosition().getDealReference();
			if (!expected.equals(dealReference)) {
				log.error("Unexpected deal reference: " + dealReference + " (expected: " + expected + ")");
			}
		}

		return dealReference;
	}

	@Override
	public List<Account> getAccountList() {
		GetAccountsResponse response = new GetAccountsExecutor(this).execute();
		return response.toList();
	}

	@Override
	public synchronized Set<String> closeAllPositions() {
		activityCache.clear();

		// List open positions
		List<MarketPosition> positions = getPositionList();

		// Close all open positions!
		Set<String> dealReferences = new LinkedHashSet<>();
		for (MarketPosition position : positions) {
			String dealReference = closePosition(position);
			dealReferences.add(dealReference);
		}
		return dealReferences;
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
		return marketsCache.get(epic, () -> new GetMarketsExecutor(IgIndexRestService.this, epic).execute());
	}

	@Override
	public PriceList getPriceList(IgIndexInstrument instrument, UnitChrono unit, int size) {
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
	public PriceList getPriceList(IgIndexInstrument instrument, UnitChrono unit, LocalDateTime from, LocalDateTime to) {

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
			getMarketNavigationCache().remove(id);
		}
		return getMarketNavigationCache().get(id, () -> new GetMarketNavigationExecutor(IgIndexRestService.this, id).execute().getMarketNavigation());
	}

	@Override
	public List<Market> searchMarkets(String text) {
		return new SearchMarketsExecutor(this, text).execute().getMarkets();
	}

	@Override
	public String openPosition(String epic, TradeDirection direction, BigDecimal size, BigDecimal stopLoss, BigDecimal stopProfit) {
		activityCache.clear();
		OpenPositionRequest request = new OpenPositionRequest(epic, direction, size, stopLoss, stopProfit);
		OpenPositionResponse response = new OpenPositionExecutor(this, request).execute();
		return response.getDealReference();
	}

	@Override
	public ActivityList getActivityList(boolean latest) {
		if (latest) {
			activityCache.clear();
		}
		// Currently limiting to the past 3 months ...
		LocalDate date = LocalDate.now().minus(Period.ofMonths(3));
		LocalDateTime dateTime = Dates.toLocalDateTime(date);
		return activityCache.get(date, () -> new GetActivityExecutor(IgIndexRestService.this, dateTime).execute().getActivities());
	}

	@Override
	public ActivityList getActivityList(LocalDateTime from) {
		return new GetActivityExecutor(IgIndexRestService.this, from).execute().getActivities();
	}

}
