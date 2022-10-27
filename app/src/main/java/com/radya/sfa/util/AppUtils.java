package com.radya.sfa.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.radya.sfa.Constant;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ConnectionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aderifaldi on 2018-02-10.
 */

public class AppUtils {

    public static boolean ENABLE_LOG = true;

    public static void logD(String TAG, String msg) {
        if (ENABLE_LOG) {
            try {
                if (msg == null) {
                    throw new NullPointerException();
                }

                Log.d(TAG, msg);
            } catch (NullPointerException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static float pixelsToSp(Context context, Float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px.floatValue() / scaledDensity;
    }

    public static float spToPixel(Context context, Float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp.floatValue() * scaledDensity;
    }

    public static int getDip(int value, Context context) {
        return (int) TypedValue.applyDimension(1, (float) value, context.getResources().getDisplayMetrics());
    }

    public static int getDip(int value, DisplayMetrics display) {
        return (int) TypedValue.applyDimension(1, (float) value, display);
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((double) ((float) dp * displayMetrics.density) + 0.5D);
    }

    @TargetApi(13)
    public static Point getScreenSize(Activity act) {
        Point size = new Point();
        WindowManager w = act.getWindowManager();
        if (Build.VERSION.SDK_INT >= 13) {
            w.getDefaultDisplay().getSize(size);
        } else {
            Display d = w.getDefaultDisplay();
            size.x = d.getWidth();
            size.y = d.getHeight();
        }

        return size;
    }

    public static Point getScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Point size = new Point();
        size.x = metrics.widthPixels;
        size.y = metrics.heightPixels;
        return size;
    }

    @TargetApi(16)
    public static void removeGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }

    }

    @TargetApi(16)
    public static void setBackground(View view, Drawable draw) {
        if (Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(draw);
        } else {
            view.setBackground(draw);
        }

    }

    public static String getStringFile(InputStream input) throws IOException {
        InputStreamReader is = new InputStreamReader(input);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);

        for (String read = br.readLine(); read != null; read = br.readLine()) {
            sb.append(read);
        }

        return sb.toString();
    }

    public static Point getSizeAspecRatioWithInputWidth(int widthDesired, int widthBitmap, int heightBitmap) {
        int heightDesired = widthDesired * heightBitmap / widthBitmap;
        Point point = new Point(widthDesired, heightDesired);
        return point;
    }

    public static Point getSizeAspecRatioWithInputHeight(int heightDesired, int widthBitmap, int heightBitmap) {
        int widthDesired = heightDesired * widthBitmap / heightBitmap;
        Point point = new Point(widthDesired, heightDesired);
        return point;
    }

    public static Double getJarak(Double latitude, Double longitude, Double cabangLatitude, Double cabangLongitude) {
        double R = 6371.0D;
        double dLat = Math.toRadians(cabangLatitude.doubleValue() - latitude.doubleValue());
        double dLon = Math.toRadians(cabangLongitude.doubleValue() - longitude.doubleValue());
        double lat1 = Math.toRadians(latitude.doubleValue());
        double lat2 = Math.toRadians(cabangLatitude.doubleValue());
        double a = Math.sin(dLat / 2.0D) * Math.sin(dLat / 2.0D) + Math.sin(dLon / 2.0D) * Math.sin(dLon / 2.0D) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2.0D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
        double d = R * c;
        return Double.valueOf(d);
    }

    public static boolean deleteFolder(File dir) {
        if (dir.exists()) {
            File[] fileList = dir.listFiles();

            for (int i = 0; i < fileList.length; ++i) {
                if (fileList[i].isDirectory()) {
                    deleteFolder(fileList[i]);
                } else {
                    fileList[i].delete();
                }
            }

            return dir.delete();
        } else {
            return false;
        }
    }

    @TargetApi(11)
    public static void setAlpha(float alpha, View view) {
        if (Build.VERSION.SDK_INT >= 11) {
            view.setAlpha(alpha);
        } else {
            int intAlpha = (int) (255.0F * alpha);
            view.setAlpha((float) intAlpha);
        }

    }

    @TargetApi(16)
    public static void setBackgroundDrawable(Drawable drawable, View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }

    }

    public static boolean isValidEmailAddress(String email){
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(Constant.Validation.EMAIL_ADDRESS_EXPRESSION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (!matcher.matches()){
            return false;
        }else {
            return true;
        }

    }

    public static String bin2hex(String input) {
        byte[] data = input.getBytes();
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }

    public static void showLoading(ProgressBar progress){
        progress.setVisibility(View.VISIBLE);
    }

    public static void dismissLoading(ProgressBar progress){
        progress.setVisibility(View.GONE);
    }

    public static void showAuthToken(){
        Log.d("HomeActivity", "auth-token : " + PrefManager.getString(Constant.Preference.User.AUTH_TOKEN));
    }

}
