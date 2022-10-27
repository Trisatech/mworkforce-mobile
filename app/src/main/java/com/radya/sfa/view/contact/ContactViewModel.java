package com.radya.sfa.view.contact;

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
import com.radya.sfa.view.contact.list.ContactList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ContactViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> contactListResponse, contactAddResponse;

    public LiveData<ApiResponse> getContactListResponse() {
        return contactListResponse;
    }

    public LiveData<ApiResponse> getContactAddResponse() {
        return contactAddResponse;
    }

    public void getContactList(ConnectionManager connectionManager, JsonObject jsonObject, int limit, int offset) {
        contactListResponse = new MediatorLiveData<>();
        connectionManager.needCacheData(Constant.Cache.CACHE_CONTACT_LIST);

        Call<ContactList> call = new ApiRequest().call(true).GetContactList(jsonObject, limit, offset);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                contactListResponse.setValue(apiResponse);
            }
        });

    }

    public void contactAdd(ConnectionManager connectionManager, RequestBody StoreName, RequestBody StoreAddress,
                           RequestBody StoreCity, RequestBody StoreDistrict, RequestBody StoreVillage,
                           RequestBody StoreStatus, RequestBody StoreAge, RequestBody StoreType,
                           RequestBody WidthRoad, RequestBody BrandingName, RequestBody StoreLatitude,
                           RequestBody StoreLongitude, RequestBody Note, RequestBody OwnerName,
                           RequestBody OwnerAddress, RequestBody OwnerCity, RequestBody OwnerDistrict,
                           RequestBody OwnerVillage, RequestBody OwnerPhoneNumber, RequestBody PICName,
                           RequestBody PICPhoneNumber, MultipartBody.Part PhotoIdCard,
                           MultipartBody.Part PhotoNPWP, MultipartBody.Part BrandingPhoto, MultipartBody.Part StorePhoto) {
        contactAddResponse = new MediatorLiveData<>();
        connectionManager.needCacheData(Constant.Cache.CACHE_CONTACT_LIST);

        Call<BaseModel> call = new ApiRequest().call(true).ContactAdd(StoreName, StoreAddress,
                StoreCity, StoreDistrict, StoreVillage, StoreStatus, StoreAge, StoreType, WidthRoad, BrandingName,
                StoreLatitude, StoreLongitude, Note, OwnerName, OwnerAddress, OwnerCity, OwnerDistrict,
                OwnerVillage, OwnerPhoneNumber, PICName, PICPhoneNumber, PhotoIdCard, PhotoNPWP,
                BrandingPhoto, StorePhoto);
        connectionManager.callApi(call, new ConnectionCallback() {
            @Override
            public void onFinishRequest(LiveData r) {
                ApiResponse apiResponse = (ApiResponse) r.getValue();
                contactAddResponse.setValue(apiResponse);
            }
        });

    }

}
