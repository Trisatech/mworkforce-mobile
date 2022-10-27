package com.radya.sfa.view.assignment.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class AssignmentDetailPayment implements Serializable{


    /**
     * payment_id : 1d63bf5b-7157-451b-83c4-2b31c8bfd871
     * assignment_id : null
     * invoice_code : I201805022
     * invoice_amount : 1000000.0
     * payment_amount : 500000.0
     * payment_debt : 500000.0
     * payment_channel : 2
     * transfer_bank : null
     * transfer_amount : 0.0
     * transfer_date : null
     * giro_number : 4321
     * giro_due_date : 2018-07-27T02:34:02
     * giro_photo : http://mobiforce.blob.core.windows.net/attachment/9d810991-705d-4407-9432-176064bd9bdf
     * giro_blob_name : null
     * giro_amount : 100000.0
     * cash_amount : 0.0
     */

    @SerializedName("payment_id")
    private String paymentId;
    @SerializedName("assignment_id")
    private String assignmentId;
    @SerializedName("invoice_code")
    private String invoiceCode;
    @SerializedName("invoice_amount")
    private double invoiceAmount;
    @SerializedName("payment_amount")
    private double paymentAmount;
    @SerializedName("payment_debt")
    private double paymentDebt;
    @SerializedName("payment_channel")
    private int paymentChannel;
    @SerializedName("transfer_bank")
    private String transferBank;
    @SerializedName("transfer_amount")
    private double transferAmount;
    @SerializedName("transfer_date")
    private String transferDate;
    @SerializedName("giro_number")
    private String giroNumber;
    @SerializedName("giro_due_date")
    private String giroDueDate;
    @SerializedName("giro_photo")
    private String giroPhoto;
    @SerializedName("giro_blob_name")
    private String giroBlobName;
    @SerializedName("giro_amount")
    private double giroAmount;
    @SerializedName("cash_amount")
    private double cashAmount;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getPaymentDebt() {
        return paymentDebt;
    }

    public void setPaymentDebt(double paymentDebt) {
        this.paymentDebt = paymentDebt;
    }

    public int getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(int paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getTransferBank() {
        return transferBank;
    }

    public void setTransferBank(String transferBank) {
        this.transferBank = transferBank;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getGiroNumber() {
        return giroNumber;
    }

    public void setGiroNumber(String giroNumber) {
        this.giroNumber = giroNumber;
    }

    public String getGiroDueDate() {
        return giroDueDate;
    }

    public void setGiroDueDate(String giroDueDate) {
        this.giroDueDate = giroDueDate;
    }

    public String getGiroPhoto() {
        return giroPhoto;
    }

    public void setGiroPhoto(String giroPhoto) {
        this.giroPhoto = giroPhoto;
    }

    public String getGiroBlobName() {
        return giroBlobName;
    }

    public void setGiroBlobName(String giroBlobName) {
        this.giroBlobName = giroBlobName;
    }

    public double getGiroAmount() {
        return giroAmount;
    }

    public void setGiroAmount(double giroAmount) {
        this.giroAmount = giroAmount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }
}
