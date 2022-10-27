package com.radya.sfa.view.dashboard;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class Attendance extends BaseModel{

    @SerializedName("data")
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
