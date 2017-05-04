package com.nazar.exchange.utils;

import com.nazar.exchange.data.remote.xml.Valute;

/**
 * Created by nazar on 30/04/2017.
 */

public class ValuteConverter {

    Valute valuteFrom = null, valuteTo;

    public ValuteConverter() {
    }

    public void setValuteFrom(Valute valuteFrom) {
        this.valuteFrom = valuteFrom;
    }

    public void setValuteTo(Valute valuteTo) {
        this.valuteTo = valuteTo;
    }

    public Float convert(String quantity) {

        quantity = quantity.replace(",", ".");

        Float result = Float.valueOf(0);

        if (valuteFrom == null || valuteTo == null || quantity.equals("")) {
            return result;
        }

        Float fQuantity = Float.valueOf(quantity);

        float fFrom = fQuantity * (valuteFrom.getValueFloat() / valuteFrom.getNominalInt());
        float fTo = valuteTo.getValueFloat() / valuteTo.getNominalInt();
        result = fFrom / fTo;
        return result;
    }
}
