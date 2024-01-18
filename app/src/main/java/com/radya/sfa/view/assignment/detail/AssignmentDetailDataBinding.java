package com.radya.sfa.view.assignment.detail;


import androidx.databinding.BaseObservable;

import com.radya.sfa.Constant;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.view.assignment.Assignment;

public class AssignmentDetailDataBinding extends BaseObservable {

    private Assignment data;

    public AssignmentDetailDataBinding(Assignment data) {
        this.data = data;
    }

    public String getAssignment_id() {
        return data.getAssignmentId();
    }

    public String getAssignment_code() {
        return data.getAssignmentCode();
    }

    public String getAssignment_name() {
        return data.getAssignmentName();
    }

    public String getAssignment_address() {
        return data.getAssignmentAddress();
    }

    public String getAssignment_status_code() {
        return data.getAssignmentStatusCode();
    }

    public double getAssignment_longitude() {
        return data.getAssignmentLatitude();
    }

    public double getAssignment_latitude() {
        return data.getAssignmentLongitude();
    }

    public String getAssignment_start_time() {
        String startTime = DateUtils.convertStringDate(data.getAssignmentDate(), Constant.DateFormat.MEDIUM);
        return startTime;
    }

    public String getRemarks() {
        return data.getRemarks();
    }

    public String getStatus() {
        return data.getStatus();
    }

    public String getContactName() {
        return data.getContact().getContact_name();
    }

    public String getContactNumber() {
        return data.getContact().getContact_number();
    }

    public String getTaskTime(){
        return DateUtils.convertStringDate(data.getAssignmentDate(), Constant.TimeFormat.MEDIUM);
    }

}
