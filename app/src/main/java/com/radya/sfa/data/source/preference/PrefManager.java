package com.radya.sfa.data.source.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.helper.aes256.AES256;

/**
 * Created by aderifaldi on 2018-01-09.
 */

public class PrefManager {

    private static final String DEFAULT_STRING = "";
    private static final int DEFAULT_INT = 0;
    private static final boolean DEFAULT_BOOLEAN = false;

    private static SharedPreferences sharedPreferences;

    public static void saveString(String key, String value) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, AES256.getEncryptedString(value)).apply();
    }

    public static void saveInt(String key, int value) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }


    public static void saveLong(String key, long value) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public static void saveBoolean(String key, Boolean value) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static String getString(String key) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return AES256.getDecriyptedString(sharedPreferences.getString(key, DEFAULT_STRING));
    }

    public static int getInt(String key) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, DEFAULT_INT);
    }

    public static Long getLong(String key) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, DEFAULT_INT);
    }

    public static Boolean getBoolean(String key) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN);
    }

}
