package com.radya.sfa.view.splashscreen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.JsonUtils;
import com.radya.sfa.util.LocationUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.auth.AuthActivity;
import com.radya.sfa.view.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends BaseActivity {

    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private boolean isUserLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        ButterKnife.bind(this);

        isUserLogin = MyApplication.getInstance().isLogin();
        String userToken = MyApplication.getInstance().getAuthToken();

        initAppConfig();
        requestLocation();

    }

    private void initAppConfig() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        JsonObject object = JsonUtils.loadJSONFromAsset(this, Constant.JSON_CONFIG_PATH);

        Config config = gson.fromJson(object, Config.class);
        int locationUpdateInterval = config.getData().getLocationUpdateInverval();

        PrefManager.saveInt(Constant.Preference.Config.LOCATION_UPDATE_INTERVAL, locationUpdateInterval);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constant.Permission.LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationUtils locationUtils = new LocationUtils(this);
                    locationUtils.checkGPSAccess(intentHandler());
                }
                break;
        }
    }

    public void requestLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        Constant.Permission.LOCATION);
            } else {
                LocationUtils locationUtils = new LocationUtils(this);
                locationUtils.checkGPSAccess(intentHandler());
            }
        } else {
            LocationUtils locationUtils = new LocationUtils(this);
            locationUtils.checkGPSAccess(intentHandler());
        }

    }

    private Runnable intentHandler() {
        return new Runnable() {
            @Override
            public void run() {
                if (!isUserLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.Bundle.PAGE, Constant.Page.LOGIN);
                    IntentUtils.runSplashDelay(SplashScreenActivity.this, AuthActivity.class, bundle);
                } else {
                    IntentUtils.runSplashDelay(SplashScreenActivity.this, HomeActivity.class);
                }
            }
        };
    }
}
