package com.robindrew.trading.igindex.platform.rest.executor.getpricelist;

import java.time.LocalDateTime;

import com.robindrew.trading.igindex.IgIndexInstrument;

public class GetPriceListRequest {

	private final IgIndexInstrument instrument;
	private final PriceResolution resolution;
	private LocalDateTime from = null;
	private LocalDateTime to = null;
	private int max = 0;
	private int pageSize = 0;
	private int pageNumber = 0;

	public GetPriceListRequest(IgIndexInstrument instrument, PriceResolution resolution) {
		this.instrument = instrument;
		this.resolution = resolution;
	}

	public IgIndexInstrument getInstrument() {
		return instrument;
	}

	public PriceResolution getResolution() {
		return resolution;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public int getMax() {
		return max;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public GetPriceListRequest setFrom(LocalDateTime from) {
		this.from = from;
		return this;
	}

	public GetPriceListRequest setTo(LocalDateTime to) {
		this.to = to;
		return this;
	}

	public GetPriceListRequest setMax(int max) {
		this.max = max;
		return this;
	}

	public GetPriceListRequest setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public GetPriceListRequest setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

}
