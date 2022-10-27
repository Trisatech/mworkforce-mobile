package com.radya.sfa.view.invoice;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class Invoice implements Serializable{

    @SerializedName("invoice_id")
    private String invoice_id;
    @SerializedName("invoice_code")
    private String invoice_code;
    @SerializedName("assignment_code")
    private String assignment_code;
    @SerializedName("amount")
    private long amount;
    @SerializedName("status")
    private String status;
    @SerializedName("is_selected")
    private boolean isSelected;
    @SerializedName("type")
    private int type;

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getInvoice_code() {
        return invoice_code;
    }

    public void setInvoice_code(String invoice_code) {
        this.invoice_code = invoice_code;
    }

    public String getAssignment_code() {
        return assignment_code;
    }

    public void setAssignment_code(String assignment_code) {
        this.assignment_code = assignment_code;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
