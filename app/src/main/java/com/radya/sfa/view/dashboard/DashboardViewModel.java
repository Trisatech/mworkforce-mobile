package com.radya.sfa.view.dashboard;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.radya.sfa.Constant;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.remote.ConnectionCallback;
import com.radya.sfa.data.source.remote.ConnectionManager;
import com.radya.sfa.view.agent.AgentList;
import com.radya.sfa.view.dashboard.news.DashboardNews;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class DashboardViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> attendanceCheckResponse, checkinResponse,
            checkOutResponse, agentListResponse, newsListResponse, dashboardResponse;

    public LiveData<ApiResponse> getAttendanceCheckResponse() {
        return attendanceCheckResponse;
    }

    public LiveData<ApiResponse> getCheckInResponse() {
        return checkinResponse;
    }

    public LiveData<ApiResponse> getCheckOutResponse() {
        return checkOutResponse;
    }

    public LiveData<ApiResponse> getAgentListResponse() {
        return agentListResponse;
    }

    public LiveData<ApiResponse> getNewsListResponse() {
        return newsListResponse;
    }

    public LiveData<ApiResponse> getDashboardResponse() {
        return dashboardResponse;
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

    public void checkIn(ConnectionManager connectionManager, RequestBody Latitude, RequestBody Longitude,
                        RequestBody StartTime, RequestBody UserId, RequestBody Mode, MultipartBody.Part Attachment) {
        checkinResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).CheckIn(Latitude, Longitude,
                StartTime, UserId, Mode, Attachment);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                checkinResponse.setValue(apiResponse);
            }
        });

    }

    public void checkInDriver(ConnectionManager connectionManager, RequestBody Latitude, RequestBody Longitude,
                              RequestBody StartTime, RequestBody UserId, RequestBody Mode,
                              RequestBody Distance, MultipartBody.Part Attachment) {
        checkinResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).CheckIn2(Latitude, Longitude,
                StartTime, UserId, Mode, Distance, Attachment);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                checkinResponse.setValue(apiResponse);
            }
        });

    }

    public void checkOut(ConnectionManager connectionManager, RequestBody Latitude, RequestBody Longitude,
                         RequestBody EndTime, RequestBody UserId, RequestBody Mode,
                         RequestBody Distance, MultipartBody.Part Attachment) {
        checkOutResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).CheckOut(Latitude, Longitude,
                EndTime, UserId, Mode, Distance, Attachment);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                checkOutResponse.setValue(apiResponse);
            }
        });

    }

    public void getAgentList(ConnectionManager connectionManager, String role) {
        agentListResponse = new MediatorLiveData<>();

        Call<AgentList> call = new ApiRequest().call(true).GetAgentList(role);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                agentListResponse.setValue(apiResponse);
            }
        });

    }

    public void getNewsList(ConnectionManager connectionManager, int offset) {
        newsListResponse = new MediatorLiveData<>();

        Call<DashboardNews> call = new ApiRequest().call(false).GetNewsList(Constant.Api.LIMIT, offset);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                newsListResponse.setValue(apiResponse);
            }
        });

    }

    public void getDashboard(ConnectionManager connectionManager, String date) {
        dashboardResponse = new MediatorLiveData<>();

        Call<Dashboard> call = new ApiRequest().call(true).Dashboard(date);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                dashboardResponse.setValue(apiResponse);
            }
        });

    }

}
