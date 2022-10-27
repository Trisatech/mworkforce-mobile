package com.radya.sfa.view.settings;

import com.google.gson.annotations.SerializedName;

public class SyncAssignmentComplete {

    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("assignment_complete")
    private String assignment_complete;
    @SerializedName("processed_time")
    private String processed_time;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAssignment_complete() {
        return assignment_complete;
    }

    public String getProcessed_time(){
        return processed_time;
    }
}
