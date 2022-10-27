package com.radya.sfa.util;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.radya.sfa.Constant;

import java.io.IOException;
import java.io.InputStream;


public class StringUtils {

    @SuppressLint("DefaultLocale")
    public static String formatMoney(String prefix, long money, char separator, String end) {
        return prefix + String.format("%,d", Long.valueOf(money)).replace(',', separator) + end;
    }

    public static String getRpCurency(long money) {
        return formatMoney(Constant.RP, money, '.', "");
    }

    public static String clearSign(String idr){
        return idr.replace(".", "");
    }

}
