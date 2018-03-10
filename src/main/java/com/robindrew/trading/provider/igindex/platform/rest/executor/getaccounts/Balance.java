package com.robindrew.trading.provider.igindex.platform.rest.executor.getaccounts;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Balance extends IgJsonObject {

	private final BigDecimal balance;
	private final BigDecimal deposit;
	private final BigDecimal profitLoss;
	private final BigDecimal available;

	public Balance(IJson object) {
		balance = object.getBigDecimal("balance");
		deposit = object.getBigDecimal("deposit");
		profitLoss = object.getBigDecimal("profitLoss");
		available = object.getBigDecimal("available");
	}

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
