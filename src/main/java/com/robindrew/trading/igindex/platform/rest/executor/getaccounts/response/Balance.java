package com.robindrew.trading.igindex.platform.rest.executor.getaccounts.response;

import java.math.BigDecimal;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class Balance extends IgIndexJsonObject {

	private BigDecimal balance;
	private BigDecimal deposit;
	private BigDecimal profitLoss;
	private BigDecimal available;

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public BigDecimal getProfitLoss() {
		return profitLoss;
	}

	public BigDecimal getProfit() {
		if (profitLoss.doubleValue() >= 0.0) {
			return profitLoss;
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getLoss() {
		if (profitLoss.doubleValue() < 0.0) {
			return profitLoss.abs();
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getAvailable() {
		return available;
	}
}
