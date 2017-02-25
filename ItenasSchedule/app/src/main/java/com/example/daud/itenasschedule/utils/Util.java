package com.example.daud.itenasschedule.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Debam on 7/29/2016.
 */

public class Util {

    public static void saveString(Context activity, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        if (editor.commit()) {
            Log.d("SP", "commited " + value);
        } else {
            Log.d("SP", "not commited");
        }
    }

    public static void saveBoolean(Context activity, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        if (editor.commit()) {
            Log.d("SP", "commited " + value);
        } else {
            Log.d("SP", "not commited");
        }
    }

    public static String getGcmToken(Context ctx){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String value = sharedPreferences.getString(
                Constant.SharedPref.TOKEN, null);
        return value;
    }

    public static String getMsisdn(Context ctx){
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sf.getString(Constant.SharedPref.MSISDN, null);
    }

    public static boolean haveLogged(Context ctx){
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sf.getBoolean(Constant.SharedPref.LOGGED, false);
    }
}
