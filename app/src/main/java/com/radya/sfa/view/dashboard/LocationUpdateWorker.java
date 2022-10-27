package com.radya.sfa.view.dashboard;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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

import androidx.work.Worker;
import retrofit2.Call;

public class LocationUpdateWorker extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        logWork();
        return Result.SUCCESS;
    }

    private void showLog(String message){
        AppUtils.logD("Location Update Worker", "location update: " + message);
    }


    private void logWork() {
        showLog("doing work!!");

        if (MyApplication.getInstance() != null){
            showLog("get context success!!");
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApplication.getInstance());

            if (MyApplication.getInstance().getActivity() != null){
                showLog("get activity success!!");

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MyApplication.getInstance().getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
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
