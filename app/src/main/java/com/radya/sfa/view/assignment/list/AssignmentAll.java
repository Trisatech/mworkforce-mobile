package com.radya.sfa.view.assignment.list;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.view.assignment.Assignment;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class AssignmentAll extends BaseModel implements Serializable {

    @SerializedName("data")
    private List<Assignment> data;

    public List<Assignment> getData() {
        return data;
    }

    public void setData(List<Assignment> data) {
        this.data = data;
    }
}
