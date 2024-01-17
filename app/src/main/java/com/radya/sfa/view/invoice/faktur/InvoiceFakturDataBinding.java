package com.radya.sfa.view.invoice.faktur;


import androidx.databinding.BaseObservable;

import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.view.invoice.Invoice;

public class InvoiceFakturDataBinding extends BaseObservable {

    private Invoice data;

    public InvoiceFakturDataBinding(Invoice data) {
        this.data = data;
    }

    public String getInvoice_id() {
        return data.getInvoice_id();
    }

    public String getInvoice_code() {
        return data.getInvoice_code();
    }

    public String getAssignment_code() {
        return data.getAssignment_code();
    }

    public String getAmount() {
        long amount = data.getAmount();
        return StringUtils.getRpCurency(amount);
    }

    public String getStatus() {
        return data.getStatus();
    }

    public boolean isSelected() {
        return data.isSelected();
    }

}
