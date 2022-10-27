package com.radya.sfa.data.source.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.data.AppDatabase;
import com.radya.sfa.helper.aes256.AES256;

/**
 * Created by RadyaLabs PC on 12/12/2017.
 */

public class CacheManager {

    private static final String DEFAULT_STRING = "";
    private static SharedPreferences sharedPreferences;

    public static void storeCache(String cacheType, String data) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(cacheType, data).apply();
    }

    public static JsonObject loadCache(String cacheType) {
        try {
            sharedPreferences = MyApplication.getInstance().getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
            return new JsonParser().parse(sharedPreferences.getString(cacheType, DEFAULT_STRING)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
