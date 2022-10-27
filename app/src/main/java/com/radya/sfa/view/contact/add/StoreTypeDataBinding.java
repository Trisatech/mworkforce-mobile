package com.radya.sfa.view.contact.add;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class StoreTypeDataBinding extends BaseObservable {

    private StoreType data;

    public StoreTypeDataBinding(StoreType data) {
        this.data = data;
    }

    public String getType_code() {
        return data.getType_code();
    }

    public String getType_name() {
        return data.getType_name();
    }
}
