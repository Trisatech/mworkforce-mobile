package com.radya.sfa.view.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.remote.service.ConnectionCallback;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.NetworkUtils;

import retrofit2.Call;

public class LocationUpdateWorker extends Worker {

    public LocationUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        logWork();
        return Result.success();
    }

    private void showLog(String message) {
        AppUtils.logD("Location Update Worker", "location update: " + message);
    }


    private void logWork() {
        showLog("doing work!!");

        if (MyApplication.getInstance() != null) {
            showLog("get context success!!");
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApplication.getInstance());

            if (MyApplication.getInstance().getActivity() != null) {
                showLog("get activity success!!");

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MyApplication.getInstance().getActivity(), location -> {
                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                if (location.getLatitude() != 0) {

                                    MyApplication.getInstance().saveCurrentLocation(location.getLatitude(), location.getLongitude());

                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty(Constant.Api.Params.LATITUDE, location.getLatitude());
                                    jsonObject.addProperty(Constant.Api.Params.LONGITUDE, location.getLongitude());
                                    jsonObject.addProperty(Constant.Api.Params.TYPE, 2);
                                    jsonObject.addProperty(Constant.Api.Params.DATE, DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
                                    jsonObject.addProperty(Constant.Api.Params.USER_ID, "123");

                                    Call<BaseModel> call = new ApiRequest().call(true).UpdateLocation(jsonObject);
                                    NetworkUtils.getConnectionManagerService().callApi(call, new ConnectionCallback() {
                                        @Override
                                        public void onFinishRequest(ApiResponse r) {
                                            //do nothing
                                            if (r.getData() != null) {
                                                showLog("location update success!!");
                                            }
                                        }
                                    });

                                } else {
                                    showLog("get location failed!!");
                                }
                            } else {
                                showLog("get location failed!!");
                            }
                        });

            } else {
                showLog("activity null!!");
            }

        } else {
            showLog("context null!!");
        }

    }

}
