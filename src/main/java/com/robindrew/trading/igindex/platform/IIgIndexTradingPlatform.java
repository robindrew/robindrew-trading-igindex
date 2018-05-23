package com.robindrew.trading.igindex.platform;

import com.robindrew.trading.igindex.IIgIndexInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.streaming.IIgIndexStreamingService;
import com.robindrew.trading.platform.ITradingPlatform;

public interface IIgIndexTradingPlatform extends ITradingPlatform<IIgIndexInstrument> {

	IIgIndexRestService getRestService();

	@Override
	IIgIndexStreamingService getStreamingService();

}
