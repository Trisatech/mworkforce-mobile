package com.radya.sfa.util;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {

    public static JsonObject loadJSONFromAsset(Activity activity, String jsonPath) {
        String jsonString = "";

        try {
            InputStream is = activity.getAssets().open(jsonPath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        return json.getAsJsonObject();
    }

    public static JsonObject getJson(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }
}
