package com.radya.sfa.view.invoice.master;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class InvoicePaymentMethod {

    @SerializedName("payment_method_code")
    private int payment_method_code;
    @SerializedName("payment_method_name")
    private String payment_method_name;

    public int getPayment_method_code() {
        return payment_method_code;
    }

    public void setPayment_method_code(int payment_method_code) {
        this.payment_method_code = payment_method_code;
    }

    public String getPayment_method_name() {
        return payment_method_name;
    }

    public void setPayment_method_name(String payment_method_name) {
        this.payment_method_name = payment_method_name;
    }

    public InvoicePaymentMethod(int payment_method_code, String payment_method_name) {
        this.payment_method_code = payment_method_code;
        this.payment_method_name = payment_method_name;
    }

}
