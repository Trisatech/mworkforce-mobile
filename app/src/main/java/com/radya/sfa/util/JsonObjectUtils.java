package com.radya.sfa.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonObjectUtils {

    public static Object getJson(String stringJson, Class<?> modelClass) {

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            JsonObject object = new JsonParser().parse(stringJson).getAsJsonObject();

            return gson.fromJson(object, modelClass);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
