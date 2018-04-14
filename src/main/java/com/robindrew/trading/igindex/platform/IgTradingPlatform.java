package com.robindrew.trading.igindex.platform;

import static com.robindrew.trading.igindex.platform.rest.executor.getaccounts.AccountType.SPREADBET;

import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.platform.position.IgPositionService;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.streaming.IgStreamingService;
import com.robindrew.trading.platform.TradingPlatform;

public class IgTradingPlatform extends TradingPlatform implements IIgTradingPlatform {

	private final IIgRestService rest;
	private final IgPositionService position;
	private final IgStreamingService streaming;

	public IgTradingPlatform(IIgRestService rest) {
		this.rest = Check.notNull("rest", rest);
		this.position = new IgPositionService(rest, SPREADBET);
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

}
