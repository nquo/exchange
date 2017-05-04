package com.nazar.exchange.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nazar.exchange.data.ValutesDataSource;
import com.nazar.exchange.data.local.ValutesPersistenceContract.ValuteEntry;
import com.nazar.exchange.data.remote.xml.Valute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazar.kvashko on 28 April 2017.
 */

public class ValutesLocalDataSource implements ValutesDataSource {

    private static ValutesLocalDataSource INSTANCE;

    private ValutesDBHelper mDbHelper;

    public ValutesLocalDataSource(Context context) {
        mDbHelper = new ValutesDBHelper(context);
    }

    @Override
    public void getValutes(final LoadValutesCallback callback) {

        List<Valute> valutes = new ArrayList<Valute>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ValuteEntry.COLUMN_NAME_NUMCODE,
                ValuteEntry.COLUMN_NAME_CHORCODE,
                ValuteEntry.COLUMN_NAME_NOMINAL,
                ValuteEntry.COLUMN_NAME_NAME,
                ValuteEntry.COLUMN_NAME_VALUE
        };

        Cursor c = db.query(
                ValuteEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {

                String numCode = c.getString(c.getColumnIndexOrThrow(ValuteEntry.COLUMN_NAME_NUMCODE));
                String charCode = c.getString(c.getColumnIndexOrThrow(ValuteEntry.COLUMN_NAME_CHORCODE));
                String nominal = c.getString(c.getColumnIndexOrThrow(ValuteEntry.COLUMN_NAME_NOMINAL));
                String name = c.getString(c.getColumnIndexOrThrow(ValuteEntry.COLUMN_NAME_NAME));
                String value = c.getString(c.getColumnIndexOrThrow(ValuteEntry.COLUMN_NAME_VALUE));

                Valute valute = new Valute(numCode, charCode, nominal, name, value);
                valutes.add(valute);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (valutes.isEmpty()) {
            // table is new or empty.
            callback.onDataNotAvailable();
        } else {
            callback.onValutesLoaded(valutes);
        }
    }

    @Override
    public void saveValutes(List<Valute> valutes) {

        if (!valutes.isEmpty()) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

//            delete all records
            db.delete(ValuteEntry.TABLE_NAME, null, null);

            ContentValues values = new ContentValues();

            for (Valute valute : valutes) {
                values.clear();
                values.put(ValuteEntry.COLUMN_NAME_NUMCODE, valute.getNumCode());
                values.put(ValuteEntry.COLUMN_NAME_CHORCODE, valute.getCharCode());
                values.put(ValuteEntry.COLUMN_NAME_NOMINAL, valute.getNominal());
                values.put(ValuteEntry.COLUMN_NAME_NAME, valute.getName());
                values.put(ValuteEntry.COLUMN_NAME_VALUE, valute.getValue());
                db.insert(ValuteEntry.TABLE_NAME, null, values);
            }

            db.close();
        }
    }

    public static ValutesLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ValutesLocalDataSource(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
