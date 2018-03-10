package com.robindrew.trading.provider.igindex.platform.sink;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.filter.PriceCandleListSortedFilter;
import com.robindrew.trading.price.candle.io.list.sink.DailyFilePriceCandleListConsumer;
import com.robindrew.trading.price.candle.io.list.sink.PriceCandleListFilteredSink;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;
import com.robindrew.trading.price.candle.io.stream.sink.QueuedPriceCandleSink;
import com.robindrew.trading.price.candle.line.formatter.PriceCandleLineFormatter;

/**
 * An price candle file, writing every tick. Uses a queue to asynchronously write candles to file.
 */
public class PriceCandleTickFileSink implements IPriceCandleStreamSink, AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleTickFileSink.class);

	private final IInstrument instrument;
	private final File directory;
	private final DailyFilePriceCandleListConsumer fileSink;
	private final PriceCandleListFilteredSink filteredSink;
	private final QueuedPriceCandleSink queueSink;

	public PriceCandleTickFileSink(IInstrument instrument, File directory) {
		this.instrument = Check.notNull("instrument", instrument);
		this.directory = Check.existsDirectory("directory", directory);

		this.fileSink = new DailyFilePriceCandleListConsumer(directory, instrument.getName(), new PriceCandleLineFormatter());
		this.filteredSink = new PriceCandleListFilteredSink(fileSink, new PriceCandleListSortedFilter());
		this.queueSink = new QueuedPriceCandleSink(instrument, filteredSink);

		log.info("Writing price ticks for {} to {}", instrument, fileSink.getDirectory());
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public File getDirectory() {
		return directory;
	}

	@Override
	public void close() {
		queueSink.close();
	}

	public void start() {
		queueSink.start();
	}

	@Override
	public String getName() {
		return queueSink.getName();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		queueSink.putNextCandle(candle);
	}

}
