package com.radya.sfa.view.report;


import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.Constant;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.StringUtils;

public class ReportListDataBinding extends BaseObservable {

    private Report data;

    public ReportListDataBinding(Report data) {
        this.data = data;
    }

    public String getUserId() {
        return data.getUserId();
    }

    public String getUserCode() {
        return data.getUserCode();
    }

    public String getUserName() {
        return data.getUserName();
    }

    public String getName() {
        return data.getName();
    }

    public String getStartTime() {
        if (data.getStartTime() != null){
            return DateUtils.convertStringDate(data.getStartTime(), Constant.DateFormat.XSHORT) ;
        } else {
            return "-";
        }
    }

    public String getEndTime() {
        if (data.getEndTime() != null){
            return DateUtils.convertStringDate(data.getEndTime(), Constant.DateFormat.XSHORT);
        } else {
            return "-";
        }
    }

    public String getTotalTaskCompleted() {
        return "" + data.getTotalTaskCompleted();
    }

    public String getTotalTaskInProgress() {
        return "" + data.getTotalTaskInProgress();
    }

    public String getTotalTaskFailed() {
        return "" + data.getTotalTaskFailed();
    }

    public String getTotalTask() {
        return getTotalTaskCompleted() + "/" + "" + data.getTotalTask();
    }

    public String getTotalWorkTime() {
        return "" + data.getTotalWorkTime() + " Jam";
    }

    public String getTotalLossTime() {
        return "" + data.getTotalLossTime() + " Jam";
    }

    public String getTotalWorkInStore(){
        return String.format("%.2f", data.getTotalTimeAtStore()) + " Jam";
    }

    public String getTotalInvoice(){
        return StringUtils.getRpCurency(data.getTotalInvoice()) ;
    }

    public String getTotalVisit(){
        if (data.getListVisit().size() != 0){
            return "" + data.getListVisit().size();
        }else {
            return "0";
        }
    }
}
