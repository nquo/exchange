package com.nazar.exchange.data.local;

import android.provider.BaseColumns;

/**
 * Created by nazar on 28/04/2017.
 */

public final class ValutesPersistenceContract {

    public ValutesPersistenceContract() {
    }

    /* example
    <NumCode>036</NumCode>
    <CharCode>AUD</CharCode>
    <Nominal>1</Nominal>
    <Name>Австралийский доллар</Name>
    <Value>42,6125</Value>
     */

    public static abstract class ValuteEntry implements BaseColumns {
        public static final String TABLE_NAME = "valute";
        public static final String COLUMN_NAME_NUMCODE = "numcode";
        public static final String COLUMN_NAME_CHORCODE = "charcode";
        public static final String COLUMN_NAME_NOMINAL = "nominal";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_VALUE = "value";
    }
}
