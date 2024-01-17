package com.radya.sfa.view.report;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.radya.sfa.Constant;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.ConnectionCallback;
import com.radya.sfa.data.source.remote.ConnectionManager;

import retrofit2.Call;

public class ReportViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> response;

    public LiveData<ApiResponse> getResponse() {
        return response;
    }

    public void getReportList(ConnectionManager connectionManager, String date) {
        response = new MediatorLiveData<>();

        Call<ReportList> call = new ApiRequest().call(true).ReportList(date);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                response.setValue(apiResponse);
            }
        });
    }

    public void getReportDetail(ConnectionManager connectionManager, String id, String date) {
        response = new MediatorLiveData<>();

        Call<ReportDetail> call = new ApiRequest().call(true).ReportDetail(id, date);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                response.setValue(apiResponse);
            }
        });
    }

}
