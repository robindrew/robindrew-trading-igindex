package com.robindrew.trading.igindex.platform.streaming;

import com.robindrew.trading.igindex.IIgInstrument;
import com.robindrew.trading.platform.streaming.IStreamingService;

public interface IIgStreamingService extends IStreamingService<IIgInstrument> {

	boolean isConnected();

	void connect();

}
