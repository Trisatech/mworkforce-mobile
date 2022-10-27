package com.radya.sfa.view.splashscreen;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class Config extends BaseModel {

    @SerializedName("data")
    private ConfigData data;

    public ConfigData getData() {
        return data;
    }

    @NoArgsConstructor
    @Data
    public static class ConfigData {
        @SerializedName("update_location_interval")
        private int locationUpdateInverval;

        public int getLocationUpdateInverval() {
            return locationUpdateInverval;
        }

        public void setLocationUpdateInverval(int locationUpdateInverval) {
            this.locationUpdateInverval = locationUpdateInverval;
        }
    }

}
