package com.wibmothon.fbank.util;

import android.content.Context;

public class Model {
    private static Context context;

    public static Context getContext() {

        return context;
    }

    public static void setContext(Context context) {

        Model.context = context;
    }

}
