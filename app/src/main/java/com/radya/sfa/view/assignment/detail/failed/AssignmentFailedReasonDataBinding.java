package com.radya.sfa.view.assignment.detail.failed;

import android.databinding.BaseObservable;

public class AssignmentFailedReasonDataBinding extends BaseObservable {

    private AssignmentFailedReason.AssignmentReason data;

    public AssignmentFailedReasonDataBinding(AssignmentFailedReason.AssignmentReason data) {
        this.data = data;
    }

    public String getReasonName(){
        return data.getReason_name();
    }

}
