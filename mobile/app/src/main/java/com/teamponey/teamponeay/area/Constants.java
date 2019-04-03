package com.teamponey.teamponeay.area;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Constants {
    public static final boolean LOGGING = false;
    public static final int RC_SIGN_IN = 9001;
    public static String server = "http://10.29.125.134:8080";
    public static String getClientToken() {
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(MainActivity.getInstance());
        return preferences.getString("access_token", null);
    }
}