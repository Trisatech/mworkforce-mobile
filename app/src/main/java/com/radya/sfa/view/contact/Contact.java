package com.radya.sfa.view.contact;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class Contact implements Serializable {

    @SerializedName("customer_code")
    private String customer_code;
    @SerializedName("contact_name")
    private String contact_name;
    @SerializedName("contact_number")
    private String contact_number;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("contact_id")
    private String contact_id;
    @SerializedName("position")
    private String position;
    @SerializedName("email")
    private String email;
    @SerializedName("secondary_email")
    private String secondary_email;
    @SerializedName("photo")
    private String photo;

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondary_email() {
        return secondary_email;
    }

    public void setSecondary_email(String secondary_email) {
        this.secondary_email = secondary_email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
