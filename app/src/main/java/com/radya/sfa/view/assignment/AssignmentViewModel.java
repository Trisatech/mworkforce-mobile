package com.radya.sfa.view.assignment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.remote.ConnectionCallback;
import com.radya.sfa.data.source.remote.ConnectionManager;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.assignment.detail.failed.AssignmentFailedReason;
import com.radya.sfa.view.assignment.list.AssignmentAll;
import com.radya.sfa.view.assignment.list.AssignmentList;
import com.radya.sfa.view.assignment.product.AssignmentProduct;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class AssignmentViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> assignmentListResponse, assignmentListResponseOnProgress,
            assignmentCalendarResponse, assignmentAllProgressResponse,assignmentAllCompleteResponse,
            assignmentDetailResponse, assignmentStartResponse, assignmentCompleteResponse,
            assignmentSearchResponse, assignmentGasolineResponse, asignmentProductResponse,
            assignmentProductOrderResponse, assignmentReasonResponse, assignmentFailedReaponse;

    public LiveData<ApiResponse> getAssignmentListResponse() {
        return assignmentListResponse;
    }

    public LiveData<ApiResponse> getAssignmentCalendarResponse() {
        return assignmentCalendarResponse;
    }

    public LiveData<ApiResponse> getAssignmentDetailResponse() {
        return assignmentDetailResponse;
    }

    public LiveData<ApiResponse> getAssignmentStartResponse() {
        return assignmentStartResponse;
    }

    public LiveData<ApiResponse> getAssignmentCompleteResponse() {
        return assignmentCompleteResponse;
    }

    public LiveData<ApiResponse> getAssignmentSearchResponse() {
        return assignmentSearchResponse;
    }

    public LiveData<ApiResponse> getAssignmentGasolineResponse() {
        return assignmentGasolineResponse;
    }

    public LiveData<ApiResponse> getAsignmentProductResponse() {
        return asignmentProductResponse;
    }


    public LiveData<ApiResponse> getAssignmentProductOrderResponse() {
        return assignmentProductOrderResponse;
    }

    public LiveData<ApiResponse> getAssignmentFailedReaponse() {
        return assignmentFailedReaponse;
    }

    public LiveData<ApiResponse> getAssignmentReasonResponse() {
        return assignmentReasonResponse;
    }

    public LiveData<ApiResponse> getAssignmentListResponseOnProgress() {
        return assignmentListResponseOnProgress;
    }

    public LiveData<ApiResponse> getAssignmentAllProgressResponse() {
        return assignmentAllProgressResponse;
    }

    public LiveData<ApiResponse> getAssignmentAllCompleteResponse() {
        return assignmentAllCompleteResponse;
    }

    public void getAssignmentList(ConnectionManager connectionManager, int status, String from,
                                  String to, int limit, int offset) {
        assignmentListResponse = new MediatorLiveData<>();

        Call<AssignmentList> call = new ApiRequest().call(true)
                .GetAssignmentList(status, from, to, limit, offset);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentListResponse.setValue(apiResponse);
            }
        });

    }

    public void getAssignmentAllOnProgress(ConnectionManager connectionManager, String from, String to) {

        connectionManager.needCacheData(Constant.Cache.CACHE_TASK_LIST);

        assignmentAllProgressResponse = new MediatorLiveData<>();

        Call<AssignmentAll> call = new ApiRequest().call(true)
                .GetAssignmentAll(from, to, 0);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentAllProgressResponse.setValue(apiResponse);
            }
        });

    }

    public void getAssignmentAllOnComplete(ConnectionManager connectionManager, String from, String to) {

        assignmentAllCompleteResponse = new MediatorLiveData<>();

        Call<AssignmentAll> call = new ApiRequest().call(true)
                .GetAssignmentAll(from, to, 1);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentAllCompleteResponse.setValue(apiResponse);
            }
        });

    }

    public void getAssignmentListOnProgress(ConnectionManager connectionManager, int status, String from,
                                  String to, int limit, int offset) {
        assignmentListResponseOnProgress = new MediatorLiveData<>();
        connectionManager.needCacheData(Constant.Cache.CACHE_TASK_LIST);

        Call<AssignmentList> call = new ApiRequest().call(true)
                .GetAssignmentList(status, from, to, limit, offset);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentListResponseOnProgress.setValue(apiResponse);
            }
        });

    }

    public void getAssignmentByCalendar(ConnectionManager connectionManager, String date, int limit, int offset) {
        assignmentCalendarResponse = new MediatorLiveData<>();

        Call<AssignmentList> call = new ApiRequest().call(true).GetAssignmentByCalendar(date, date, limit, offset);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentCalendarResponse.setValue(apiResponse);
            }
        });

    }

    public void getAssignmentDetail(ConnectionManager connectionManager, String id) {
        assignmentDetailResponse = new MediatorLiveData<>();

        Call<AssignmentDetail> call = new ApiRequest().call(true).GetAssignmentDetail(id);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentDetailResponse.setValue(apiResponse);
            }
        });

    }

    public void assignmentStart(ConnectionManager connectionManager, String id, JsonObject jsonObject, boolean is_online) {
        assignmentStartResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).AssignmentStart(id, jsonObject, is_online);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentStartResponse.setValue(apiResponse);
            }
        });
    }

    public void assignmentComplete(ConnectionManager connectionManager,
                                   String id,
                                   RequestBody Latitude,
                                   RequestBody Longitude,
                                   RequestBody ProcessedTime,
                                   RequestBody AssignmentStatus,
                                   boolean is_online) {
        assignmentCompleteResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).AssignmentComplete(id, Latitude, Longitude, ProcessedTime, AssignmentStatus, is_online);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentCompleteResponse.setValue(apiResponse);
            }
        });
    }

    public void assignmentFailed(ConnectionManager connectionManager,
                                 String id,
                                 RequestBody Latitude,
                                 RequestBody Longitude,
                                 RequestBody AssignmentStatus,
                                 RequestBody ReasonCode,
                                 MultipartBody.Part file,
                                 RequestBody failedReasonPlus) {
        assignmentFailedReaponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).AssignmentFailed(id,
                Latitude, Longitude, AssignmentStatus, ReasonCode, file, failedReasonPlus);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentFailedReaponse.setValue(apiResponse);
            }
        });
    }

    public void searchAssignment(ConnectionManager connectionManager, JsonObject jsonObject,
                                 int status, int limit, int offset) {
        assignmentSearchResponse = new MediatorLiveData<>();

        Call<AssignmentList> call = new ApiRequest().call(true).SearchAssignment(jsonObject, status, limit, offset);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentSearchResponse.setValue(apiResponse);
            }
        });
    }

    public void assignmentGasoline(ConnectionManager connectionManager, int type, RequestBody address,
                                   RequestBody remarks, RequestBody liter, RequestBody latitude,
                                   RequestBody longitude, RequestBody eventDate,
                                   MultipartBody.Part attachment) {
        assignmentGasolineResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).AssignmentGasoline(type,
                address, remarks, liter, latitude, longitude, eventDate, attachment);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentGasolineResponse.setValue(apiResponse);
            }
        });
    }

    public void assignmentProductList(ConnectionManager connectionManager) {
        asignmentProductResponse = new MediatorLiveData<>();

        Call<AssignmentProduct> call = new ApiRequest().call(true).GetProductList();
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                asignmentProductResponse.setValue(apiResponse);
            }
        });
    }

    public void assignmentProductOrder(ConnectionManager connectionManager, JsonObject jsonObject) {
        assignmentProductOrderResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).ProductOrder(jsonObject);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentProductOrderResponse.setValue(apiResponse);
            }
        });
    }

    public void assignmentFailedReason(ConnectionManager connectionManager) {
        assignmentReasonResponse = new MediatorLiveData<>();

        Call<AssignmentFailedReason> call = new ApiRequest().call(true).GetAssignmentReason();
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                assignmentReasonResponse.setValue(apiResponse);
            }
        });
    }

}
