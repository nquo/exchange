package com.nazar.exchange.data.remote.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by nazar.kvashko on 04 May 2017.
 */

@Root
public class ValCurs {

    @Attribute(name = "Date")
    private String date;

    @Attribute(name = "name")
    private String name;

    @ElementList(entry="Valute", inline=true)
    private List<Valute> valutes;

    public List<Valute> getValutes() {
        return valutes;
    }

}


