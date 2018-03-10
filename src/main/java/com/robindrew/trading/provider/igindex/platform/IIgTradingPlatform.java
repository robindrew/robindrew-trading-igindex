package com.robindrew.trading.provider.igindex.platform;

import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.provider.igindex.platform.rest.IIgRestService;

public interface IIgTradingPlatform extends ITradingPlatform {

	IIgRestService getRestService();

}
