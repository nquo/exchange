package com.nazar.exchange.data;

import com.nazar.exchange.data.remote.xml.Valute;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nazar.kvashko on 28 April 2017.
 */

public class ValutesRepository implements ValutesDataSource {

    private static ValutesRepository INSTANCE = null;

    private final ValutesDataSource mValutesRemoteDataSource;
    private final ValutesDataSource mValutesLocalDataSource;

    Map<String, Valute> mCachedValutes;
    boolean oldCache = true;

    ValutesRepository(ValutesDataSource valutesRemoteDataSource, ValutesDataSource valutesLocalDataSource) {
        mValutesRemoteDataSource = valutesRemoteDataSource;
        mValutesLocalDataSource = valutesLocalDataSource;
    }

    @Override
    public void getValutes(final LoadValutesCallback callback) {
        // respond cached
        if (mCachedValutes != null && !oldCache) {
            callback.onValutesLoaded(new ArrayList<>(mCachedValutes.values()));
            return;
        }

        mValutesRemoteDataSource.getValutes(new LoadValutesCallback() {
            @Override
            public void onValutesLoaded(List<Valute> valutes) {
                //yes
                saveValutes(valutes);
                callback.onValutesLoaded(valutes);
            }

            @Override
            public void onDataNotAvailable() {
                //no
                mValutesLocalDataSource.getValutes(callback);
            }
        });
    }

    @Override
    public void saveValutes(List<Valute> valutes) {
        refreshCache(valutes);
        mValutesLocalDataSource.saveValutes(valutes);
    }

    private void refreshCache(List<Valute> valutes) {
        if (mCachedValutes == null) {
            mCachedValutes = new LinkedHashMap<>();
        }
        mCachedValutes.clear();
        for (Valute valute : valutes) {
            mCachedValutes.put(valute.getCharCode(), valute);
        }
        oldCache = false;
    }


    public static ValutesRepository getInstance(ValutesDataSource valutesRemoteDataSource,
                                                ValutesDataSource valutesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ValutesRepository(valutesRemoteDataSource, valutesLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
