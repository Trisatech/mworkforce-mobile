package com.radya.sfa.view.invoice.payment;

import android.databinding.BaseObservable;

import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.StringUtils;

public class InvoicePaymentDataBinding extends BaseObservable{

    private InvoicePayment data;

    public InvoicePaymentDataBinding(InvoicePayment data) {
        this.data = data;
    }

    public String getPayment_type() {
        return data.getPayment_type();
    }

    public String getAmount() {
        long amount = data.getAmount();
        return StringUtils.getRpCurency(amount);
    }
}
