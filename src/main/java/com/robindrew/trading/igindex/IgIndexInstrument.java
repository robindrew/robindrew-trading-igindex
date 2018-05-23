package com.robindrew.trading.igindex;

import static com.robindrew.trading.Instruments.AUD_USD;
import static com.robindrew.trading.Instruments.BITCOIN;
import static com.robindrew.trading.Instruments.BRENT_CRUDE_OIL;
import static com.robindrew.trading.Instruments.DAX_30;
import static com.robindrew.trading.Instruments.DOW_JONES_30;
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
import static com.robindrew.trading.Instruments.US_CRUDE_OIL;
import static com.robindrew.trading.Instruments.XAG_USD;
import static com.robindrew.trading.Instruments.XAU_USD;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingProvider;

public class IgIndexInstrument extends Instrument implements IIgIndexInstrument {

	/** AUD/USD. */
	public static final IgIndexInstrument SPOT_AUD_USD = new IgIndexInstrument("CS.D.AUDUSD.TODAY.IP", AUD_USD);
	/** EUR/JPY. */
	public static final IgIndexInstrument SPOT_EUR_JPY = new IgIndexInstrument("CS.D.EURJPY.TODAY.IP", EUR_JPY);
	/** EUR/USD. */
	public static final IgIndexInstrument SPOT_EUR_USD = new IgIndexInstrument("CS.D.EURUSD.TODAY.IP", EUR_USD);
	/** GBP/USD. */
	public static final IgIndexInstrument SPOT_GBP_USD = new IgIndexInstrument("CS.D.GBPUSD.TODAY.IP", GBP_USD);
	/** USD/CHF. */
	public static final IgIndexInstrument SPOT_USD_CHF = new IgIndexInstrument("CS.D.USDCHF.TODAY.IP", USD_CHF);
	/** USD/JPY. */
	public static final IgIndexInstrument SPOT_USD_JPY = new IgIndexInstrument("CS.D.USDJPY.TODAY.IP", USD_JPY);

	/** Bitcoin. */
	public static final IgIndexInstrument SPOT_BITCOIN = new IgIndexInstrument("CS.D.BITCOIN.TODAY.IP", BITCOIN);
	/** Ether. */
	public static final IgIndexInstrument SPOT_ETHER = new IgIndexInstrument("CS.D.ETHUSD.TODAY.IP", ETHER);
	/** Ripple. */
	public static final IgIndexInstrument SPOT_RIPPLE = new IgIndexInstrument("CS.D.XRPUSD.TODAY.IP", RIPPLE);
	/** Litecoin. */
	public static final IgIndexInstrument SPOT_LITECOIN = new IgIndexInstrument("CS.D.LTCUSD.TODAY.IP", LITECOIN);

	/** FTSE 100. */
	public static final IgIndexInstrument WEEKDAY_FTSE_100 = new IgIndexInstrument("IX.D.FTSE.DAILY.IP", FTSE_100);
	/** DAX. */
	public static final IgIndexInstrument WEEKDAY_DAX = new IgIndexInstrument("IX.D.DAX.DAILY.IP", DAX_30);
	/** S&amp;P 500. */
	public static final IgIndexInstrument WEEKDAY_SP_500 = new IgIndexInstrument("IX.D.SPTRD.DAILY.IP", SP_500);
	/** DOW JONES. */
	public static final IgIndexInstrument WEEKDAY_DOW_JONES = new IgIndexInstrument("IX.D.DOW.DAILY.IP", DOW_JONES_30);

	/** FTSE 100 (Weekend). */
	// public static final IgInstrument SUNDAY_FTSE_100 = new IgInstrument("IX.D.SUNFUN.DAILY.IP", FTSE_100);
	/** DAX (Weekend). */
	// public static final IgInstrument SUNDAY_DAX = new IgInstrument("IX.D.SUNDAX.DAILY.IP", DAX_30);
	/** DOW JONES (Weekend). */
	// public static final IgInstrument SUNDAY_DOW_JONES = new IgInstrument("IX.D.SUNDOW.DAILY.IP", DOW_JONES_30);

	/** US CRUDE. */
	public static final IgIndexInstrument SPOT_US_CRUDE = new IgIndexInstrument("CC.D.CL.USS.IP", US_CRUDE_OIL);
	/** BRENT CRUDE. */
	public static final IgIndexInstrument SPOT_BRENT_CRUDE = new IgIndexInstrument("CC.D.LCO.USS.IP", BRENT_CRUDE_OIL);

	/** GOLD. */
	public static final IgIndexInstrument SPOT_GOLD = new IgIndexInstrument("CS.D.USCGC.TODAY.IP", XAU_USD);
	/** SILVER. */
	public static final IgIndexInstrument SPOT_SILVER = new IgIndexInstrument("CS.D.USCSI.TODAY.IP", XAG_USD);

	private final IPricePrecision precision = new PricePrecision(2);

	public IgIndexInstrument(String epic, IInstrument underlying) {
		super(epic, underlying);
	}

	public IgIndexInstrument(String epic, InstrumentType type) {
		super(epic, type);
	}

	@Override
	public String getEpic() {
		return getName();
	}

	@Override
	public IPricePrecision getPrecision() {
		return precision;
	}

	@Override
	public ITradingProvider getProvider() {
		return TradingProvider.IGINDEX;
	}

}
