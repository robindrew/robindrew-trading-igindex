package com.robindrew.trading.igindex.platform;

import static com.robindrew.trading.igindex.platform.rest.executor.getaccounts.AccountType.SPREADBET;

import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.IIgInstrument;
import com.robindrew.trading.igindex.platform.account.IgAccountService;
import com.robindrew.trading.igindex.platform.position.IgPositionService;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.streaming.IgStreamingService;
import com.robindrew.trading.platform.TradingPlatform;

public class IgTradingPlatform extends TradingPlatform<IIgInstrument> implements IIgTradingPlatform {

	private final IIgRestService rest;
	private final IgAccountService account;
	private final IgPositionService position;
	private final IgStreamingService streaming;

	public IgTradingPlatform(IIgRestService rest) {
		this.rest = Check.notNull("rest", rest);
		this.account = new IgAccountService(rest, SPREADBET);
		this.position = new IgPositionService(rest);
		this.streaming = new IgStreamingService(rest);
	}

	@Override
	public IgStreamingService getStreamingService() {
		return streaming;
	}

	@Override
	public IgPositionService getPositionService() {
		return position;
	}

	@Override
	public IIgRestService getRestService() {
		return rest;
	}

	@Override
	public IgAccountService getAccountService() {
		return account;
	}

}
