package com.radya.sfa.view.report;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.util.List;

public class ReportList extends BaseModel {

    @SerializedName("data")
    private List<Report> data;

    public List<Report> getData() {
        return data;
    }

}
