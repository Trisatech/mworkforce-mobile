package com.radya.sfa.view.contact.list;


import androidx.databinding.BaseObservable;

import com.radya.sfa.view.contact.Contact;

public class ContactListDataBinding extends BaseObservable {

    private Contact data;

    public ContactListDataBinding(Contact data) {
        this.data = data;
    }

    public String getContact_code(){
        return data.getCustomer_code();
    }

    public String getContact_name() {
        return data.getContact_name();
    }

    public String getContact_number() {
        return data.getContact_number();
    }

    public String getRemarks() {
        return data.getRemarks();
    }

    public String getPosition(){
        return data.getPosition();
    }

    public String getEmail(){
        return data.getEmail();
    }

    public String getSecondaryEmail(){
        return data.getSecondary_email();
    }

    public String getPhoto(){
        return data.getPhoto();
    }

}
