package com.radya.sfa.view.auth;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.remote.ConnectionCallback;
import com.radya.sfa.data.source.remote.ConnectionManager;

import retrofit2.Call;

public class AuthViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> userLoginResponse, userLogoutResponse;

    public LiveData<ApiResponse> getUserLoginResponse() {
        return userLoginResponse;
    }

    public LiveData<ApiResponse> getUserLogoutResponse() {
        return userLogoutResponse;
    }

    public void login(ConnectionManager connectionManager, JsonObject jsonObject) {
        userLoginResponse = new MediatorLiveData<>();

        Call<Login> call = new ApiRequest().call(false).Login(jsonObject);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                userLoginResponse.setValue(apiResponse);
            }
        });

    }

    public void logout(ConnectionManager connectionManager) {
        userLogoutResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).Logout();
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                userLogoutResponse.setValue(apiResponse);
            }
        });

    }

}
