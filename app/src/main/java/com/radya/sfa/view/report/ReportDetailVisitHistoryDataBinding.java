package com.radya.sfa.view.report;

import android.databinding.BaseObservable;

import com.radya.sfa.Constant;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.StringUtils;

public class ReportDetailVisitHistoryDataBinding extends BaseObservable {

    private ReportDetail.ReportDetailData.VisitHistory data;

    public ReportDetailVisitHistoryDataBinding(ReportDetail.ReportDetailData.VisitHistory data) {
        this.data = data;
    }

    public String getCustomerName() {
        return data.getCustomerName();
    }

    public String getStatus() {
        return data.getStatus();
    }

    public String getStartDate(){
        if(data.getStartedTime() != null){
            String startDate = DateUtils.convertStringDate(data.getStartedTime(), Constant.DateFormat.XSHORT);
            return  "Mulai: " + startDate;
        }else {
            return  "Mulai: -";
        }

    }

    public String getWaktuGoogle(){
        return "Waktu google: " + "" + String.format("%.2f", data.getLossTime()) + " Jam";
    }

    public String getEndDate(){
        if (data.getEndedTime() != null){
            String endDate = DateUtils.convertStringDate(data.getEndedTime(), Constant.DateFormat.XSHORT);
            return  "Selesai: " + endDate;
        }else {
            return  "Selesai: -" ;
        }

    }

    public String getWaktuTerbuarg(){
        double terbuang = data.getSalesTime() - data.getLossTime();
        return "Waktu tebuang: " + String.format("%.2f", terbuang) + " Jam";
    }

    public String getInvoiceAmount(){
        return "Tagihan: " + StringUtils.getRpCurency(data.getInvoiceAmount()) ;
    }


}
