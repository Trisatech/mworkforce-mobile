package com.radya.sfa.view.invoice.payment;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class InvoicePayment implements Serializable{

    @SerializedName("payment_type")
    private String payment_type;
    @SerializedName("amount")
    private long amount;
    @SerializedName("giro_photo")
    private File giro_photo;
    @SerializedName("no_giro")
    private String no_giro;
    @SerializedName("giro_due_date")
    private String giro_due_date;

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public File getGiro_photo() {
        return giro_photo;
    }

    public void setGiro_photo(File giro_photo) {
        this.giro_photo = giro_photo;
    }

    public String getNo_giro() {
        return no_giro;
    }

    public void setNo_giro(String no_giro) {
        this.no_giro = no_giro;
    }

    public String getGiro_due_date() {
        return giro_due_date;
    }

    public void setGiro_due_date(String giro_due_date) {
        this.giro_due_date = giro_due_date;
    }

    public InvoicePayment(String payment_type, long amount, String no_giro, File giro_photo, String giro_due_date) {
        this.payment_type = payment_type;
        this.amount = amount;
        this.no_giro = no_giro;
        this.giro_photo = giro_photo;
        this.giro_due_date = giro_due_date;
    }

}
