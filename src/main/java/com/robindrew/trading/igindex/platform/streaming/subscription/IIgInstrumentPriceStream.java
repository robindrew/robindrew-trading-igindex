package com.robindrew.trading.igindex.platform.streaming.subscription;

import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.robindrew.trading.igindex.IIgInstrument;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public interface IIgInstrumentPriceStream extends IInstrumentPriceStream<IIgInstrument> {

	ExtendedTableInfo getExtendedTableInfo();

	HandyTableListener getHandyTableListener();

	SubscribedTableKey getSubscribedTableKey();

	void setKey(SubscribedTableKey key);

}
