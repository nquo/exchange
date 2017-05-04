package com.nazar.exchange.main;

import com.nazar.exchange.BasePresenter;
import com.nazar.exchange.BaseView;

import java.util.List;

/**
 * Created by nazar on 27/04/2017.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {

        void showValutes(List<String> valutes);

        void setResultText(String text);

        void setInputText(String text);

        void showErrorMessage();

        void lockUI(boolean enable);

    }

    interface Presenter extends BasePresenter {

        void loadValutes(boolean forceUpdate);

        void setValuteFrom(int position);

        void setValuteTo(int position);

        void convertRequest(String s);

        void clearInputText();
    }
}
