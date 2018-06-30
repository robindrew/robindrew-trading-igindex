package com.robindrew.trading.igindex.platform;

import static com.robindrew.common.locale.CurrencyCode.GBP;
import static com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response.AccountType.SPREADBET;

import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.IIgIndexInstrument;
import com.robindrew.trading.igindex.platform.account.IgIndexAccountService;
import com.robindrew.trading.igindex.platform.position.IgIndexPositionService;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.streaming.IIgIndexStreamingService;
import com.robindrew.trading.igindex.platform.streaming.IgIndexStreamingService;
import com.robindrew.trading.platform.TradingPlatform;

public class IgIndexTradingPlatform extends TradingPlatform<IIgIndexInstrument> implements IIgIndexTradingPlatform {

	private final IIgIndexRestService rest;
	private final IgIndexAccountService account;
	private final IgIndexPositionService position;
	private final IIgIndexStreamingService streaming;

	public IgIndexTradingPlatform(IIgIndexRestService rest) {
		this.rest = Check.notNull("rest", rest);
		this.account = new IgIndexAccountService(rest, SPREADBET, GBP);
		this.position = new IgIndexPositionService(rest);
		this.streaming = new IgIndexStreamingService(rest);
	}

	@Override
	public IIgIndexStreamingService getStreamingService() {
		return streaming;
	}

	@Override
	public IgIndexPositionService getPositionService() {
		return position;
	}

	@Override
	public IIgIndexRestService getRestService() {
		return rest;
	}

	@Override
	public IgIndexAccountService getAccountService() {
		return account;
	}

}
