package com.robindrew.trading.igindex.platform.streaming;

import com.robindrew.trading.igindex.IIgIndexInstrument;
import com.robindrew.trading.platform.streaming.IStreamingService;

public interface IIgIndexStreamingService extends IStreamingService<IIgIndexInstrument> {

	boolean isConnected();

	void connect();

}
