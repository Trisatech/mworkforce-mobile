package com.radya.sfa.view.contact.add;


import androidx.databinding.BaseObservable;

public class StoreStatusDataBinding extends BaseObservable {

    private StoreStatus data;

    public StoreStatusDataBinding(StoreStatus data) {
        this.data = data;
    }

    public String getStatus_code() {
        return data.getStatus_code();
    }

    public String getStatus_name() {
        return data.getStatus_name();
    }
}
