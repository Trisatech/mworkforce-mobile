package com.radya.sfa.view.contact.add;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class StoreStatus {

    @SerializedName("status_code")
    private String status_code;
    @SerializedName("status_name")
    private String status_name;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public StoreStatus(String status_code, String status_name) {
        this.status_code = status_code;
        this.status_name = status_name;
    }

}
