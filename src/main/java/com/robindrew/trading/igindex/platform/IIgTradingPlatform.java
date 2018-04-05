package com.robindrew.trading.igindex.platform;

import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.platform.ITradingPlatform;

public interface IIgTradingPlatform extends ITradingPlatform {

	IIgRestService getRestService();

}
