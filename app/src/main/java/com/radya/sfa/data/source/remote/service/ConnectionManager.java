package com.radya.sfa.data.source.remote.service;


import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.ToastUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionManager<T> {
    private final String TAG = "CONNECTION_MANAGER";

    private ApiResponse<T> apiResponse;

    private JsonObject jsonCache;
    private String alertMessage;
    private boolean isError;
    private int responseCode;
    private int alertCode;

    private BaseModel baseModel;

    public ConnectionManager() {
    }

    public void callApi(final Call<T> call, final ConnectionCallback callback) {

        apiResponse = new ApiResponse<>();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

                responseCode = response.code();

                if (responseCode == 500){

                    alertMessage = "Internal server error";
                    ToastUtils.showToast(alertMessage);

                    apiResponse.setData(null);

                } else if (responseCode == 404){

                    alertMessage = "Service not found";
                    ToastUtils.showToast(alertMessage);

                    apiResponse.setData(null);

                } else {

                    baseModel = (BaseModel) response.body();

                    alertMessage = baseModel.getAlert().getMessage();
                    alertCode = baseModel.getAlert().getCode();
                    isError = baseModel.isError();

                    if (response.isSuccessful()) {
                        try {
                            if (!isError) {
                                if (alertCode == Constant.Api.Code.SUCCESS) {
                                    apiResponse.setData(response.body());
                                } else if (alertCode == Constant.Api.Code.LOGGED_OTHER_DEVICE) {
                                    logMessage(alertMessage);
                                } else if (alertCode == Constant.Api.Code.ALREADY_CHECK_IN) {
                                    logMessage(alertMessage);
                                } else {
                                    logMessage(alertMessage);
                                }
                            } else {
                                if (alertCode == Constant.Api.Code.INVALID_TOKEN) {
                                    logMessage(alertMessage);
                                } else {
                                    logMessage(alertMessage);
                                }
                                apiResponse.setData(null);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                            logMessage(e.getMessage());
                            apiResponse.setData(null);
                        }

                    } else {

                        if (response.errorBody() != null) {
                            try {
                                if (!"".equals(response.errorBody().string())) {
                                    alertMessage = response.errorBody().string();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        logMessage(alertMessage);
                        apiResponse.setData(null);
                    }
                }

                callback.onFinishRequest(apiResponse);

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {

                if (t instanceof IOException) {
                    alertMessage = "Koneksi internet bermasalah";
                } else {
                    alertMessage = t.getMessage();
                }

                logMessage(alertMessage);

                apiResponse.setData(null);

                callback.onFinishRequest(apiResponse);


            }
        });
    }

    private void logMessage(String alertMessage) {
        AppUtils.logD("LocationUpdate", alertMessage);
    }

}
