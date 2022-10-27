package com.radya.sfa.view.invoice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.radya.sfa.data.source.remote.ApiRequest;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.remote.ConnectionCallback;
import com.radya.sfa.data.source.remote.ConnectionManager;
import com.radya.sfa.view.invoice.verification.InvoiceVerification;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class InvoiceViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> requestOTPResponse, invoicePaymentResponse;

    public LiveData<ApiResponse> getRequestOTPResponse() {
        return requestOTPResponse;
    }

    public LiveData<ApiResponse> getInvoicePaymentResponse() {
        return invoicePaymentResponse;
    }

    public void requestOTP(ConnectionManager connectionManager, String assignmentId, JsonObject jsonObject) {
        requestOTPResponse = new MediatorLiveData<>();

        Call<InvoiceVerification> call = new ApiRequest().call(true).RequestOTP(assignmentId, jsonObject);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                requestOTPResponse.setValue(apiResponse);
            }
        });
    }

    public void invoicePayment(ConnectionManager connectionManager, RequestBody AssignmentId, RequestBody InvoiceCode,
                               RequestBody InvoiceAmount, RequestBody PaymentAmount, RequestBody PaymentDebt,
                               RequestBody PaymentChannel, RequestBody TransferAmount, RequestBody GiroAmount,
                               RequestBody CashAmount, RequestBody Otp, RequestBody TransferBank,
                               MultipartBody.Part GiroAttachment, RequestBody GiroPhoto, RequestBody GiroNumber,
                               RequestBody GiroDueDate, RequestBody GiroNumber1, RequestBody GiroPhoto1,
                               RequestBody GiroAmount1, RequestBody GiroDueDate1, RequestBody GiroNumber2,
                               RequestBody GiroPhoto2, RequestBody GiroAmount2, RequestBody GiroDueDate2,
                               RequestBody GiroNumber3, RequestBody GiroPhoto3, RequestBody GiroAmount3,
                               RequestBody GiroDueDate3, RequestBody GiroNumber4, RequestBody GiroPhoto4,
                               RequestBody GiroAmount4, RequestBody GiroDueDate4, MultipartBody.Part GiroAttachment1,
                               MultipartBody.Part GiroAttachment2, MultipartBody.Part GiroAttachment3,
                               MultipartBody.Part GiroAttachment4) {
        invoicePaymentResponse = new MediatorLiveData<>();

        Call<BaseModel> call = new ApiRequest().call(true).InvoicePayment(AssignmentId,
                InvoiceCode, InvoiceAmount, PaymentAmount, PaymentDebt, PaymentChannel, TransferAmount,
                GiroAmount, CashAmount, Otp, TransferBank, GiroAttachment, GiroPhoto, GiroNumber,
                GiroDueDate, GiroNumber1, GiroPhoto1, GiroAmount1, GiroDueDate1, GiroNumber2,
                GiroPhoto2, GiroAmount2, GiroDueDate2, GiroNumber3, GiroPhoto3, GiroAmount3,
                GiroDueDate3, GiroNumber4, GiroPhoto4, GiroAmount4, GiroDueDate4, GiroAttachment1,
                GiroAttachment2, GiroAttachment3, GiroAttachment4);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                invoicePaymentResponse.setValue(apiResponse);
            }
        });
    }

}
