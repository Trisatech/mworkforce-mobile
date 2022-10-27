package com.radya.sfa.view.contact.add;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class StoreType {

    @SerializedName("type_code")
    private String type_code;
    @SerializedName("type_name")
    private String type_name;

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public StoreType(String type_code, String type_name) {
        this.type_code = type_code;
        this.type_name = type_name;
    }

}
