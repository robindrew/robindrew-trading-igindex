package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation;

import com.robindrew.common.json.IJson;

public class GetMarketNavigationResponse {

	private final Nodes nodes;
	private final Markets markets;

	public GetMarketNavigationResponse(IJson object) {
		// {"nodes":[{"id":"93262","name":"Indices"},{"id":"165333","name":"Forex"},{"id":"93237","name":"Commodities
		// Metals Energies"},{"id":"740446","name":"Cryptocurrency"},{"id":"94005","name":"Bonds and
		// Moneymarket"},{"id":"150222","name":"ETFs, ETCs & Trackers"},{"id":"107565","name":"Shares -
		// UK"},{"id":"100801","name":"Shares - UK International (IOB)"},{"id":"133682","name":"Shares - LSE
		// (UK)"},{"id":"377740","name":"Shares - US (All Sessions)"},{"id":"105486","name":"Shares -
		// US"},{"id":"93846","name":"Shares - Australia"},{"id":"93406","name":"Shares -
		// Austria"},{"id":"93475","name":"Shares - Belgium"},{"id":"93395","name":"Shares -
		// Canada"},{"id":"93398","name":"Shares - Denmark"},{"id":"93566","name":"Shares -
		// Finland"},{"id":"93629","name":"Shares - France"},{"id":"93843","name":"Shares -
		// Germany"},{"id":"93170","name":"Shares - Greece"},{"id":"93357","name":"Shares - Hong
		// Kong"},{"id":"100380","name":"Shares - Ireland (ISEQ)"},{"id":"100307","name":"Shares - Ireland
		// (LSE)"},{"id":"105809","name":"Shares - Netherlands"},{"id":"93411","name":"Shares -
		// Norway"},{"id":"93563","name":"Shares - Portugal"},{"id":"93381","name":"Shares -
		// Singapore"},{"id":"93360","name":"Shares - South Africa"},{"id":"93429","name":"Shares -
		// Sweden"},{"id":"93401","name":"Shares - Switzerland"},{"id":"357975","name":"Sprint
		// Markets"},{"id":"656351","name":"Digital 100s: 2 Hour FX"},{"id":"656381","name":"Digital 100s: 2
		// Hourlies"},{"id":"656390","name":"Digital 100s: 20 Min Markets"},{"id":"656120","name":"Digital 100s: 5 Min
		// Markets"},{"id":"656118","name":"Digital 100s: FX"},{"id":"656416","name":"Digital 100s:
		// Hourlies"},{"id":"656119","name":"Digital 100s: Indices"},{"id":"656407","name":"Digital 100s:
		// Specials"},{"id":"656128","name":"Digital 100s:Commodities"},{"id":"110169","name":"Options (Australian,
		// Cash)"},{"id":"302518","name":"Options (Eu Stocks 50)"},{"id":"157735","name":"Options (France
		// 40)"},{"id":"125257","name":"Options (FTSE)"},{"id":"110184","name":"Options
		// (Germany)"},{"id":"157943","name":"Options (Italy 40)"},{"id":"268825","name":"Options (Netherlands
		// 25)"},{"id":"157738","name":"Options (Spain 35)"},{"id":"399997","name":"Options (Sweden
		// 30)"},{"id":"346003","name":"Options (US 500)"},{"id":"125267","name":"Options (Wall
		// St)"},{"id":"322274","name":"Options on FX Majors"},{"id":"166251","name":"Options on Metals,
		// Energies"},{"id":"10891422","name":"Weekend Indices"}],"markets":null}

		// {"nodes":null,"markets":[{"delayTime":0,"epic":"CS.D.AUDUSD.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"AUD/USD","high":7817.2,"low":7812.1,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":7811.9,"offer":7816.1,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.EURCHF.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"EUR/CHF","high":11519.4,"low":11504.3,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":11507.1,"offer":11515.3,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.EURGBP.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"EUR/GBP","high":8869.1,"low":8861.1,"percentageChange":0.0,"updateTime":"75538000","updateTimeUTC":"21:58:58","bid":8859.9,"offer":8864.9,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.EURJPY.TODAY.IP","netChange":0.0,"lotSize":1000,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"EUR/JPY","high":13336.1,"low":13325.8,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":13327.4,"offer":13334.0,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.EURUSD.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"EUR/USD","high":12254.2,"low":12248.7,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":12250.8,"offer":12253.8,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.GBPEUR.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"GBP/EUR","high":11289.0,"low":11274.0,"percentageChange":0.0,"updateTime":"75538000","updateTimeUTC":"21:58:58","bid":11280.4,"offer":11286.8,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.GBPUSD.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"GBP/USD","high":13837.0,"low":13826.8,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":13823.6,"offer":13825.6,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.USDCAD.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"USD/CAD","high":12585.9,"low":12576.7,"percentageChange":0.0,"updateTime":"75538000","updateTimeUTC":"21:58:58","bid":12577.0,"offer":12585.0,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.USDCHF.TODAY.IP","netChange":0.0,"lotSize":10,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"USD/CHF","high":9397.7,"low":9387.1,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":9390.4,"offer":9400.1,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1},{"delayTime":0,"epic":"CS.D.USDJPY.TODAY.IP","netChange":0.0,"lotSize":1000,"expiry":"DFB","instrumentType":"CURRENCIES","instrumentName":"USD/JPY","high":10882.0,"low":10875.7,"percentageChange":0.0,"updateTime":"75539000","updateTimeUTC":"21:58:59","bid":10878.7,"offer":10881.2,"otcTradeable":true,"streamingPricesAvailable":true,"marketStatus":"EDITS_ONLY","scalingFactor":1}]}

		this.nodes = object.contains("nodes") ? new Nodes(object.getObjectList("nodes")) : null;
		this.markets = object.contains("markets") ? new Markets(object.getObjectList("markets")) : null;
	}

	public MarketNavigation getMarketNavigation() {
		return new MarketNavigation(nodes, markets);
	}
}
