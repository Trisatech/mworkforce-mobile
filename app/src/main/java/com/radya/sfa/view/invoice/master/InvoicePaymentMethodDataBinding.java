package com.radya.sfa.view.invoice.master;


import androidx.databinding.BaseObservable;

public class InvoicePaymentMethodDataBinding extends BaseObservable {

    private InvoicePaymentMethod data;

    public InvoicePaymentMethodDataBinding(InvoicePaymentMethod data) {
        this.data = data;
    }

    public int getPayment_method_code() {
        return data.getPayment_method_code();
    }

    public String getPayment_method_name() {
        return data.getPayment_method_name();
    }

}
