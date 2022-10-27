package com.radya.sfa.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;

public class LocationUtils {

    private Activity activity;

    private Location currentLocation;

    public LocationUtils(Activity activity) {
        this.activity = activity;
    }

    public void checkGPSAccess(Runnable runnable) {

        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            AlertUtils alertUtils = new AlertUtils(activity);
            alertUtils.showAlert(activity.getString(R.string.alertMessageNeedGPSAccess),
                    new AlertUtils.positiveButton() {
                        @Override
                        public void onYes(DialogInterface dialogInterface) {
                            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(i);
                            activity.finish();
                        }
                    }, activity.getString(R.string.Ok));
        } else {
            runnable.run();
        }
    }

    @SuppressLint("MissingPermission")
    public void setCurrentLocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            if (location.getLatitude() != 0) {
                                currentLocation = location;
                                MyApplication.getInstance().saveCurrentLocation(location.getLatitude(), location.getLongitude());
                            } else {
                                currentLocation = MyApplication.getInstance().currentLocation();
                            }
                        } else {
                            currentLocation = MyApplication.getInstance().currentLocation();
                        }
                    }
                });

    }

    public void getDirection(String latitude, String longitude) {
        //"geo:37.7749,-122.4194"

//        String geoLocation = "google.navigation:q" + latitude + "," + longitude;
        String geoLocation = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;

        Uri gmmIntentUri = Uri.parse(geoLocation);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        }

    }

}
