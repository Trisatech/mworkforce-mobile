package com.radya.sfa.view.agent;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class AgentList extends BaseModel implements Serializable {

    @SerializedName("data")
    private List<Agent> data;

    public List<Agent> getData() {
        return data;
    }

    public void setData(List<Agent> data) {
        this.data = data;
    }
}
