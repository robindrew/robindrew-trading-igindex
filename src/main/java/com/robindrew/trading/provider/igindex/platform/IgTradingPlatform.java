package com.robindrew.trading.provider.igindex.platform;

import static com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.AccountType.SPREADBET;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.TradingPlatform;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.closed.IClosedPosition;
import com.robindrew.trading.position.order.IPositionOrder;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.provider.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.Account;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts.AccountType;
import com.robindrew.trading.trade.funds.AccountFunds;
import com.robindrew.trading.trade.funds.Cash;
import com.robindrew.trading.trade.funds.ICash;

public class IgTradingPlatform extends TradingPlatform implements IIgTradingPlatform {

	private final AccountType accountType;
	private final IIgRestService service;

	public IgTradingPlatform(IIgRestService service) {
		this.service = Check.notNull("service", service);
		this.accountType = SPREADBET;
	}

	@Override
	public IStreamingService getStreamingService() {
		return getRestService().getStreamingService();
	}

	@Override
	public IIgRestService getRestService() {
		return service;
	}

	@Override
	public List<IPosition> getAllPositions() {
		List<IPosition> positions = new ArrayList<>();
		for (IPosition position : service.getPositionList()) {
			positions.add(position);
		}
		return positions;
	}

	@Override
	public IClosedPosition closePosition(IPosition position) {
		service.closePosition(position);
		throw new UnsupportedOperationException();
	}

	@Override
	public AccountFunds getAvailableFunds() {
		Account account = service.getAccount(accountType);
		ICash available = new Cash(account.getBalance().getAvailable(), false);
		return new AccountFunds(available);
	}

	@Override
	public IHistoryService getHistoryService() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPosition openPosition(IPositionOrder order) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPricePrecision getPrecision(IInstrument instrument) {
		throw new UnsupportedOperationException();
	}
}
