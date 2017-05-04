package com.nazar.exchange;

import android.content.Context;

import com.nazar.exchange.data.ValutesRepository;
import com.nazar.exchange.data.local.ValutesLocalDataSource;
import com.nazar.exchange.data.remote.ValutesRemoteDataSource;

/**
 * Created by nazar.kvashko on 03 May 2017.
 */

public class Injection {

    public static ValutesRepository provideValutesRepository(Context context) {
        return ValutesRepository.getInstance(ValutesRemoteDataSource.getInstance(), ValutesLocalDataSource.getInstance(context));
    }
}
