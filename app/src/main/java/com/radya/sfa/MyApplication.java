package com.radya.sfa;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.support.v4.app.FragmentActivity;

import com.radya.sfa.data.AppDatabase;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.view.assignment.AssignmentViewModel;

/**
 * Created by aderifaldi on 2018-01-18.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public AssignmentViewModel getViewModel() {
        return ViewModelProviders.of((FragmentActivity) activity).get(AssignmentViewModel.class);
    }

    public void saveCurrentLocation(double latitude, double longitude) {
        PrefManager.saveLong(Constant.Preference.User.LATITUDE, Double.doubleToRawLongBits(latitude));
        PrefManager.saveLong(Constant.Preference.User.LONGITUDE, Double.doubleToRawLongBits(longitude));
    }

    public Location currentLocation() {

        try {
            Location location = new Location("");

            location.setLatitude(Double.longBitsToDouble(PrefManager.getLong(Constant.Preference.User.LATITUDE)));
            location.setLongitude(Double.longBitsToDouble(PrefManager.getLong(Constant.Preference.User.LONGITUDE)));

            return location;
        } catch (Exception e) {
            return null;
        }

    }

    public double latitude() {
        return currentLocation().getLatitude();
    }

    public double longitude() {
        return currentLocation().getLongitude();
    }

    public boolean isLogin() {
        return PrefManager.getBoolean(Constant.Preference.User.IS_LOGIN);
    }

    public String userRole() {
        return PrefManager.getString(Constant.Preference.User.USER_ROLE_CODE);
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public static AppDatabase obtainLocalDB() {
        return AppDatabase.getDatabase(getInstance());
    }

    public String getAuthToken() {
        return PrefManager.getString(Constant.Preference.User.AUTH_TOKEN);
    }

}
