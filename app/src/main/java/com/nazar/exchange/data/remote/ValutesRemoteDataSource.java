package com.nazar.exchange.data.remote;

import android.os.AsyncTask;

import com.nazar.exchange.data.ValutesDataSource;
import com.nazar.exchange.data.remote.xml.ValCurs;
import com.nazar.exchange.data.remote.xml.Valute;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Element;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazar.kvashko on 28 April 2017.
 */

public class ValutesRemoteDataSource implements ValutesDataSource {

    private static ValutesRemoteDataSource INSTANCE;

    final String XML_URL = "http://www.cbr.ru/scripts/XML_daily.asp";


    public ValutesRemoteDataSource() {
    }

    @Override
    public void getValutes(final LoadValutesCallback callback) {

        new AsyncRequest(callback).execute(XML_URL);

    }

    @Override
    public void saveValutes(List<Valute> valutes) {

    }


    private Float getValueFloat(Element element, String name) {
        return Float.parseFloat(getValue(element, name));
    }

    private Integer getValueInteger(Element element, String name) {
        return Integer.parseInt(getValue(element, name));
    }

    private String getValue(Element element, String name) {
        return element.getElementsByTagName(name).item(0).getFirstChild().getNodeValue().replace(",", ".");
    }


    class AsyncRequest extends AsyncTask<String, Integer, List<Valute>> {
        LoadValutesCallback delegate = null;

        public AsyncRequest(LoadValutesCallback asyncResponse) {
            delegate = asyncResponse;
        }

        @Override
        protected List<Valute> doInBackground(String... arg) {
            return loadContent(arg[0]);
        }

        @Override
        protected void onPostExecute(List<Valute> result) {
            if (result.isEmpty())
                delegate.onDataNotAvailable();
            else
                delegate.onValutesLoaded(result);
        }

        private List<Valute> loadContent(String link) {
            List<Valute> valutes = new ArrayList<Valute>();

            try {
                //
                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                //simplexml
                Serializer serializer = new Persister();

                ValCurs xml = serializer.read(ValCurs.class, conn.getInputStream());

                if (!xml.getValutes().isEmpty()) {
                    valutes.add(new Valute("000", "RUR", "1", "Российский рубль", "1"));
                    valutes.addAll(xml.getValutes());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return valutes;
            }
        }
    }


    public static ValutesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValutesRemoteDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
