package com.robindrew.trading.igindex.platform.rest.executor.login.response;

import java.math.BigDecimal;

public class AccountInfo {

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

	public BigDecimal getAvailable() {
		return available;
	}

}
