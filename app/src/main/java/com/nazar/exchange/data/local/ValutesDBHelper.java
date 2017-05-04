package com.nazar.exchange.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nazar.exchange.data.local.ValutesPersistenceContract.ValuteEntry;

/**
 * Created by nazar on 28/04/2017.
 */

public class ValutesDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Valutes.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ValuteEntry.TABLE_NAME + " (" +
                    ValuteEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ValuteEntry.COLUMN_NAME_NUMCODE + INTEGER_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_CHORCODE + TEXT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_NOMINAL + INTEGER_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ValuteEntry.COLUMN_NAME_VALUE + REAL_TYPE +
                    " )";


    public ValutesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
