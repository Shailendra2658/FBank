package com.wibmothon.fbank.util;

import android.content.Context;
import android.content.SharedPreferences;


import com.wibmothon.fbank.R;

import java.security.SecureRandom;

public class Utils {
    static int randomAndroidColor;
    static int color = -1;
    private static int[] androidColors = Model.getContext().getResources().getIntArray(R.array.androidcolors_dark);

    //Generate Random Color
    public static String genRandomColor() {
        randomAndroidColor = androidColors[new SecureRandom().nextInt(androidColors.length)];
        return "#" + Integer.toHexString(randomAndroidColor).substring(2);
    }

    //Save Circle Color in Shared Prefs
    public static void setCircleColor(int position, int color) {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences = Model.getContext().getSharedPreferences("colors", Context.MODE_PRIVATE);
        editor = preferences.edit().putInt(Integer.toString(position), color);
        editor.apply();
    }

    //Retrieve Circle Color from Shared Prefs
    public static int LoadCircleColor(int position) {
        SharedPreferences preferences = Model.getContext().getSharedPreferences("colors", Context.MODE_PRIVATE);
        color = preferences.getInt(Integer.toString(position), -1);
        return color;
    }

    public static String getNameInitial(String name) {
        String nameInitial;
        try {
            String[] initial = name.split(" ");
            if (initial.length == 1) {
                nameInitial = initial[0].substring(0, 1).toUpperCase();
            } else {
                nameInitial = initial[0].substring(0, 1).toUpperCase() + initial[1].substring(0, 1).toUpperCase();
            }
        } catch (Exception e) {
            return name;
        }
        return nameInitial;
    }
}
