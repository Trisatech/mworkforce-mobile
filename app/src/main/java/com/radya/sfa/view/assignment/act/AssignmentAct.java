package com.radya.sfa.view.assignment.act;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class AssignmentAct implements Serializable{

    @SerializedName("act_id")
    private int actId;
    @SerializedName("act_name")
    private String actName;
    @SerializedName("is_selected")
    private boolean isSelected;

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AssignmentAct(int actId, String actName, boolean isSelected) {
        this.actId = actId;
        this.actName = actName;
        this.isSelected = isSelected;
    }
}
