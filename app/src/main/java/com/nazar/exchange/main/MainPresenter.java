package com.nazar.exchange.main;

import com.nazar.exchange.data.ValutesDataSource;
import com.nazar.exchange.data.ValutesRepository;
import com.nazar.exchange.data.remote.xml.Valute;
import com.nazar.exchange.utils.ValuteConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazar on 27/04/2017.
 */

final class MainPresenter implements MainContract.Presenter {
    private final ValutesRepository mRepository;
    private final MainContract.View mMainView;
    private final ValuteConverter mConterter = new ValuteConverter();

    MainPresenter(ValutesRepository valutesRepository, MainContract.View view) {
        mRepository = valutesRepository;
        mMainView = view;

        mMainView.setPresenter(this);
    }

    void setupListeners() {
        mMainView.setPresenter(this);
    }


    @Override
    public void start() {
        loadValutes(false);
    }


    @Override
    public void loadValutes(boolean forceUpdate) {

        mRepository.getValutes(new ValutesDataSource.LoadValutesCallback() {
            @Override
            public void onValutesLoaded(List<Valute> valutes) {

                List<String> result = new ArrayList<String>();

                for (Valute valute : valutes) {
                    result.add(valute.getCharCode() + " " + valute.getName());
                }

                processValutes(result);

                mMainView.lockUI(false);
            }

            @Override
            public void onDataNotAvailable() {
                mMainView.lockUI(true);
                mMainView.showErrorMessage();
            }
        });

    }

    @Override
    public void setValuteFrom(final int position) {
        mRepository.getValutes(new ValutesDataSource.LoadValutesCallback() {
            @Override
            public void onValutesLoaded(List<Valute> valutes) {
                mConterter.setValuteFrom(valutes.get(position));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setValuteTo(final int position) {
        mRepository.getValutes(new ValutesDataSource.LoadValutesCallback() {
            @Override
            public void onValutesLoaded(List<Valute> valutes) {
                mConterter.setValuteTo(valutes.get(position));
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void convertRequest(String s) {
        mMainView.setResultText(String.format("%.2f", mConterter.convert(s)));
    }

    @Override
    public void clearInputText() {
        mMainView.setInputText("");
        mMainView.setResultText("0");
    }

    private void processValutes(List<String> result) {
        mMainView.showValutes(result);
    }

}
