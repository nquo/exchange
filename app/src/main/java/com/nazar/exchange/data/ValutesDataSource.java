package com.nazar.exchange.data;

import com.nazar.exchange.data.remote.xml.Valute;

import java.util.List;

/**
 * Created by nazar.kvashko on 28 April 2017.
 */

public interface ValutesDataSource {

    interface LoadValutesCallback {

        void onValutesLoaded(List<Valute> valutes);

        void onDataNotAvailable();
    }

    void getValutes(LoadValutesCallback callback);

    void saveValutes(List<Valute> valutes);
}
