package com.robindrew.trading.igindex.platform;

import static com.robindrew.trading.igindex.platform.rest.executor.getaccounts.AccountType.SPREADBET;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.Account;
import com.robindrew.trading.igindex.platform.rest.executor.getaccounts.AccountType;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.ActivityList;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.MarketPosition;
import com.robindrew.trading.platform.TradingPlatform;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.trade.funds.AccountFunds;
import com.robindrew.trading.trade.funds.Cash;
import com.robindrew.trading.trade.funds.ICash;

public class IgTradingPlatform extends TradingPlatform implements IIgTradingPlatform {

	private static final Logger log = LoggerFactory.getLogger(IgTradingPlatform.class);

	private final AccountType accountType;
	private final IIgRestService rest;

	public IgTradingPlatform(IIgRestService rest) {
		this.rest = Check.notNull("rest", rest);
		this.accountType = SPREADBET;
	}

	@Override
	public IStreamingService getStreamingService() {
		return getRestService().getStreamingService();
	}

	@Override
	public IIgRestService getRestService() {
		return rest;
	}

	@Override
	public List<MarketPosition> getAllPositions() {
		List<MarketPosition> positions = new ArrayList<>();
		for (MarketPosition position : rest.getPositionList()) {
			positions.add(position);
		}
		return positions;
	}

	@Override
	public IClosedPosition closePosition(IPosition position) {
		String dealReference = rest.closePosition(position);
		log.info("[Closed Position] {}", dealReference);

		// We only have the reference for the closed order
		// To get the details we must find the transaction in the recent activity
		ActivityList activityList = new ActivityList();
		String dealId = position.getId();
		for (int i = 1; i <= 3; i++) {

			// There is a delay between closing an order and the activity appearing ...
			// wait a short time to allow the activity to appear
			Threads.sleep(i, SECONDS);

			// Search all activity in the past five minutes
			LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(5);

			activityList = rest.getActivityList(tenMinutesAgo).withDealReference(dealReference).closedPositions();
			if (!activityList.isEmpty()) {
				break;
			}
		}
		if (activityList.isEmpty()) {
			log.error("Unable to locate activity for closed position: dealReference=" + dealReference + ", dealId=" + dealId);
		}

		return null;
	}

	@Override
	public AccountFunds getAvailableFunds() {
		Account account = rest.getAccount(accountType);
		ICash available = new Cash(account.getBalance().getAvailable(), false);
		return new AccountFunds(available);
	}

	@Override
	public IHistoryService getHistoryService() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MarketPosition openPosition(IPositionOrder order) {
		IgInstrument instrument = (IgInstrument) order.getInstrument();
		String dealReference = rest.openPosition(instrument.getEpic(), order.getDirection(), order.getTradeSize(), order.getStopLossDistance(), order.getProfitLimitDistance());
		log.info("[Opened Position] {}", dealReference);

		// Lookup the position details
		for (MarketPosition position : rest.getPositionList()) {
			if (position.getPosition().getDealReference().equals(dealReference)) {
				log.info("Id: {}", position.getId());
				log.info("Epic: {}", position.getEpic());
				log.info("Price: {}", position.getOpenPrice());
				log.info("Size: {}", position.getTradeSize());
				log.info("Direction: {}", position.getDirection());
				log.info("Currency: {}", position.getTradeCurrency());
				return position;
			}
		}
		throw new IgException("Missing position for deal: " + dealReference);
	}

	@Override
	public IPricePrecision getPrecision(IInstrument instrument) {
		throw new UnsupportedOperationException();
	}
}