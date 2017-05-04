package com.nazar.exchange.data.remote.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by nazar.kvashko on 28 April 2017.
 */

@Root
public final class Valute {
    @Attribute(name = "ID")
    private String id;

    @Element(name = "NumCode", required = true)
    private String numCode;

    @Element(name = "CharCode", required = true)
    private String charCode;

    @Element(name = "Nominal", required = true)
    private String nominal;

    @Element(name = "Name", required = true)
    private String name;

    @Element(name = "Value", required = true)
    private String value;

    public Valute() {
    }

    public Valute(String numCode, String charCode, String nominal, String name, String value) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getNominal() {
        return nominal;
    }

    public Integer getNominalInt() {
        return Integer.valueOf(nominal);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Float getValueFloat() {
        return Float.valueOf(value.replace(",", "."));
    }
}
