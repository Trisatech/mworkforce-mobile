package com.radya.sfa.data.source.remote;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 28/09/2017.
 */
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
//@DatagetData
public class BaseModel implements Serializable {

    @SerializedName("error")
    private boolean error;
    @SerializedName("force")
    private boolean force;
    @SerializedName("alert")
    private Alerts alert;

    public Alerts getAlert() {
        return alert;
    }

    public void setAlert(Alerts alert) {
        this.alert = alert;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    @NoArgsConstructor
    @Data
    public static class Alerts implements Serializable {
        @SerializedName("code")
        private int code;
        @SerializedName("message")
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
