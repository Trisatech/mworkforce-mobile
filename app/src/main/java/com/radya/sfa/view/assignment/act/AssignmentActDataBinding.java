package com.radya.sfa.view.assignment.act;


import androidx.databinding.BaseObservable;

public class AssignmentActDataBinding extends BaseObservable {

    private AssignmentAct data;

    public AssignmentActDataBinding(AssignmentAct data) {
        this.data = data;
    }

    public String getName() {
        return data.getActName();
    }

    public boolean isSelected() {
        return data.isSelected();
    }

}
