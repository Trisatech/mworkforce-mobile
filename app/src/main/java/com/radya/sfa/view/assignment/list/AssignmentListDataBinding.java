package com.radya.sfa.view.assignment.list;


import androidx.databinding.BaseObservable;

import com.radya.sfa.Constant;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;

public class AssignmentListDataBinding extends BaseObservable {

    private Assignment data;

    public AssignmentListDataBinding(Assignment data) {
        this.data = data;
    }

    public String getTaskName() {
        return data.getAssignmentName();
    }

    public String getTaskCode() {
        return data.getAssignmentCode();
    }

    public String getTaskDate() {
        return DateUtils.convertStringDate(data.getAssignmentDate(), Constant.DateFormat.SHORT);
    }

    public String getTaskTime(){
        return DateUtils.convertStringDate(data.getAssignmentDate(), Constant.TimeFormat.MEDIUM);
    }

    public String getTaskAddress() {
        return data.getAssignmentAddress();
    }

}
