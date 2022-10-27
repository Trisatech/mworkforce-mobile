package com.radya.sfa.view.invoice.master;

import android.databinding.BaseObservable;

public class InvoiceBankDataBinding extends BaseObservable {

    private InvoiceBank data;

    public InvoiceBankDataBinding(InvoiceBank data) {
        this.data = data;
    }

    public String getBank_name() {
        return data.getBank_name();
    }
}
