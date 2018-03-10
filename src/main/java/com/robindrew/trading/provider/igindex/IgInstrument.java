package com.robindrew.trading.provider.igindex;

import static com.robindrew.trading.Instruments.AUD_USD;
import static com.robindrew.trading.Instruments.BITCOIN;
import static com.robindrew.trading.Instruments.DAX;
import static com.robindrew.trading.Instruments.DOW_JONES;
import static com.robindrew.trading.Instruments.ETHER;
import static com.robindrew.trading.Instruments.EUR_JPY;
import static com.robindrew.trading.Instruments.EUR_USD;
import static com.robindrew.trading.Instruments.FTSE_100;
import static com.robindrew.trading.Instruments.GBP_USD;
import static com.robindrew.trading.Instruments.LITECOIN;
import static com.robindrew.trading.Instruments.RIPPLE;
import static com.robindrew.trading.Instruments.SP_500;
import static com.robindrew.trading.Instruments.USD_CHF;
import static com.robindrew.trading.Instruments.USD_JPY;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.InstrumentType;

public class IgInstrument extends Instrument {

	/** AUD/USD. */
	public static final IgInstrument SPOT_AUD_USD = new IgInstrument("CS.D.AUDUSD.TODAY.IP", AUD_USD);
	/** EUR/JPY. */
	public static final IgInstrument SPOT_EUR_JPY = new IgInstrument("CS.D.EURJPY.TODAY.IP", EUR_JPY);
	/** EUR/USD. */
	public static final IgInstrument SPOT_EUR_USD = new IgInstrument("CS.D.EURUSD.TODAY.IP", EUR_USD);
	/** GBP/USD. */
	public static final IgInstrument SPOT_GBP_USD = new IgInstrument("CS.D.GBPUSD.TODAY.IP", GBP_USD);
	/** USD/CHF. */
	public static final IgInstrument SPOT_USD_CHF = new IgInstrument("CS.D.USDCHF.TODAY.IP", USD_CHF);
	/** USD/JPY. */
	public static final IgInstrument SPOT_USD_JPY = new IgInstrument("CS.D.USDJPY.TODAY.IP", USD_JPY);

	/** Bitcoin. */
	public static final IgInstrument SPOT_BITCOIN = new IgInstrument("CS.D.BITCOIN.TODAY.IP", BITCOIN);
	/** Ether. */
	public static final IgInstrument SPOT_ETHER = new IgInstrument("CS.D.ETHUSD.TODAY.IP", ETHER);
	/** Ripple. */
	public static final IgInstrument SPOT_RIPPLE = new IgInstrument("CS.D.XRPUSD.TODAY.IP", RIPPLE);
	/** Litecoin. */
	public static final IgInstrument SPOT_LITECOIN = new IgInstrument("CS.D.LTCUSD.TODAY.IP", LITECOIN);

	/** FTSE 100. */
	public static final IgInstrument WEEKDAY_FTSE_100 = new IgInstrument("IX.D.FTSE.DAILY.IP", FTSE_100);
	/** DAX. */
	public static final IgInstrument WEEKDAY_DAX = new IgInstrument("IX.D.DAX.DAILY.IP", DAX);
	/** S&P 500. */
	public static final IgInstrument WEEKDAY_SP_500 = new IgInstrument("IX.D.SPTRD.DAILY.IP", SP_500);
	/** DOW JONES. */
	public static final IgInstrument WEEKDAY_DOW_JONES = new IgInstrument("IX.D.DOW.DAILY.IP", DOW_JONES);

	/** FTSE 100 (Weekend). */
	public static final IgInstrument SUNDAY_FTSE_100 = new IgInstrument("IX.D.SUNFUN.DAILY.IP", FTSE_100);
	/** DAX (Weekend). */
	public static final IgInstrument SUNDAY_DAX = new IgInstrument("IX.D.SUNDAX.DAILY.IP", DAX);
	/** DOW JONES (Weekend). */
	public static final IgInstrument SUNDAY_DOW_JONES = new IgInstrument("IX.D.SUNDOW.DAILY.IP", DOW_JONES);

	public IgInstrument(String epic, IInstrument underlying) {
		super(epic, underlying);
	}

	public IgInstrument(String epic, InstrumentType type) {
		super(epic, type);
	}

	public String getEpic() {
		return getName();
	}

}
