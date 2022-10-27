package com.radya.sfa.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.radya.sfa.Constant;
import com.radya.sfa.R;

import java.io.Serializable;

public class IntentUtils {

    public static void runSplashDelay(final Activity from, final Class<?> to) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intentTo(from, to, true);
            }
        }, Constant.SPLASH_DELAY);
    }

    public static void runSplashDelay(final Activity from, final Class<?> to, final Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intentTo(from, to, true, bundle);
            }
        }, Constant.SPLASH_DELAY);
    }

    public static void intentTo(final Activity from, final Class<?> to, final boolean isFinish) {

        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        if (isFinish) {
            from.finish();
        }
    }

    public static void intentTo(final Activity from, final Class<?> to, final boolean isFinish, Serializable data) {
        if (data != null){
            Intent intent = new Intent(from, to);
            intent.putExtra(Constant.SERIALIZABLE_NAME, data);

            from.startActivity(intent);
            from.overridePendingTransition(R.anim.left_in, R.anim.left_out);
            if (isFinish) {
                from.finish();
            }
        }
    }

    public static void intentTo(final Activity from, final Class<?> to, final boolean isFinish, Bundle data) {
        if (data != null){
            Intent intent = new Intent(from, to);
            intent.putExtras(data);

            from.startActivity(intent);
            from.overridePendingTransition(R.anim.left_in, R.anim.left_out);
            if (isFinish) {
                from.finish();
            }
        }
    }

    public static void backTo(final Activity from, final Class<?> to, final boolean isFinish) {

        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.left_back_in, R.anim.left_back_out);
        if (isFinish) {
            from.finish();
        }
    }

    public static void backTo(final Activity from, final Class<?> to, final boolean isFinish, Bundle data) {
        if (data != null){
            Intent intent = new Intent(from, to);
            intent.putExtras(data);

            from.startActivity(intent);
            from.overridePendingTransition(R.anim.left_in, R.anim.left_out);
            if (isFinish) {
                from.finish();
            }
        }
    }

    public static void backAnimation(Activity activity){
        activity.overridePendingTransition(R.anim.left_back_in, R.anim.left_back_out);
    }

}
