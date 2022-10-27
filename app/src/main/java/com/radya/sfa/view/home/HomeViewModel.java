package com.radya.sfa.view.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.remote.ConnectionCallback;
import com.radya.sfa.data.source.remote.ConnectionManager;
import com.radya.sfa.view.agent.AgentList;
import com.radya.sfa.view.dashboard.Attendance;

import retrofit2.Call;

public class HomeViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> attendanceCheckResponse;

    public LiveData<ApiResponse> getAttendanceCheckResponse() {
        return attendanceCheckResponse;
    }

    public void checkAttendance(ConnectionManager connectionManager) {
        attendanceCheckResponse = new MediatorLiveData<>();

        Call<Attendance> call = new ApiRequest().call(true).CheckAttendance(0);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                attendanceCheckResponse.setValue(apiResponse);
            }
        });

    }

}
