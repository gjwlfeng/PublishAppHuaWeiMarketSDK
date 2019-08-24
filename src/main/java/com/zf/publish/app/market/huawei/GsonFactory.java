package com.zf.publish.app.market.huawei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    public static GsonFactory sGsonFactory;

    private Gson gson;

    private GsonFactory() {
        gson= new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public static GsonFactory getInstance() {
        if (sGsonFactory == null) {
            synchronized (GsonFactory.class) {
                if (sGsonFactory == null) {
                    sGsonFactory = new GsonFactory();
                }
            }
        }
        return sGsonFactory;
    }

    public Gson getGson() {
        return gson;
    }
}
