package com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Metadata extends IgJsonObject {

	private final Allowance allowance;
	private final int size;
	private final PageData pageData;

	public Metadata(IJson object) {
		allowance = new Allowance(object.getObject("allowance"));
		size = object.getInt("size");
		pageData = new PageData(object.getObject("pageData"));
	}

	public Allowance getAllowance() {
		return allowance;
	}

	public int getSize() {
		return size;
	}

	public PageData getPageData() {
		return pageData;
	}

	public class Allowance extends IgJsonObject {

		private final int remainingAllowance;
		private final int totalAllowance;
		private final int allowanceExpiry;

		private Allowance(IJson object) {
			remainingAllowance = object.getInt("remainingAllowance");
			totalAllowance = object.getInt("totalAllowance");
			allowanceExpiry = object.getInt("allowanceExpiry");
		}

		public int getRemainingAllowance() {
			return remainingAllowance;
		}

		public int getTotalAllowance() {
			return totalAllowance;
		}

		public int getAllowanceExpiry() {
			return allowanceExpiry;
		}
	}

	public class PageData extends IgJsonObject {

		private final int pageSize;
		private final int pageNumber;
		private final int totalPages;

		private PageData(IJson object) {
			pageSize = object.getInt("pageSize");
			pageNumber = object.getInt("pageNumber");
			totalPages = object.getInt("totalPages");
		}

		public int getPageSize() {
			return pageSize;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getTotalPages() {
			return totalPages;
		}

	}

}
