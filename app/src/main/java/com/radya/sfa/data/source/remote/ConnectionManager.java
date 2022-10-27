package com.radya.sfa.data.source.remote;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.data.source.cache.CacheManager;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.list.AssignmentAll;
import com.radya.sfa.view.assignment.list.AssignmentList;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionManager<T> {
    private final String TAG = "CONNECTION_MANAGER";

    private MutableLiveData<ApiResponse> liveData;
    private ApiResponse<T> apiResponse;

    private JsonObject jsonCache;
    private String alertMessage;
    private boolean useCache;
    private boolean isError;
    private int responseCode;
    private String cacheType;
    private int alertCode;

    private BaseModel baseModel;

    public ConnectionManager() {
    }

    public void callApi(final Call<T> call, final ConnectionCallback callback) {

        liveData = new MutableLiveData<>();
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

                } else if (responseCode == 504){

                    alertMessage = "Connection Timed Out";
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
                                    if (useCache) {
                                        storeCache(response);
                                    }
                                } else if (alertCode == Constant.Api.Code.LOGGED_OTHER_DEVICE) {
                                    showToast(alertMessage);
                                } else if (alertCode == Constant.Api.Code.ALREADY_CHECK_IN) {
                                    showToast(alertMessage);
                                } else {
                                    showToast(alertMessage);
                                }

                                apiResponse.setData(response.body());
                            } else {
                                if (alertCode == Constant.Api.Code.INVALID_TOKEN) {
                                    showToast(alertMessage);
                                } else {
                                    showToast(alertMessage);
                                }
                                apiResponse.setData(null);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                            showToast(e.getMessage());
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

                        showToast(alertMessage);
                        apiResponse.setData(null);
                    }
                }

                liveData.setValue(apiResponse);
                callback.onFinishRequest(liveData);

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {

                if (t instanceof IOException) {
                    alertMessage = "Gagal terhubung dengan server";
                } else {
                    alertMessage = t.getMessage();
                }

                showToast(alertMessage);

                if (useCache) {
                    checkCache();
                } else {
                    apiResponse.setData(null);
                }

                liveData.setValue(apiResponse);
                callback.onFinishRequest(liveData);


            }
        });
    }

    private void storeCache(Response<T> response) {
        CacheManager.storeCache(cacheType, new Gson().toJson(response.body()));
    }

    private void checkCache() {
        jsonCache = CacheManager.loadCache(cacheType);
        if (cacheType == Constant.Cache.CACHE_TASK_LIST) {
            AssignmentAll cache = new GsonBuilder().create().fromJson(jsonCache, AssignmentAll.class);
            apiResponse.setData((T) cache);
        } else {
            apiResponse.setData(null);
        }
    }

    public void needCacheData(String type) {
        useCache = true;
        cacheType = type;
    }

    private void showToast(String alertMessage) {
        ToastUtils.showToast(alertMessage);
    }

}
