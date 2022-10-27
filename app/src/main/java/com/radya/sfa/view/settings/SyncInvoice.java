package com.radya.sfa.view.settings;

import com.google.gson.annotations.SerializedName;

public class SyncInvoice {


    /**
     * assignment_id : 3a8b8529-330c-4f39-a96d-8eff13d1ce34
     * invoice_code : 1234
     * invoice_amount : 300000
     * payment_amount : 0
     * payment_debt : -1000000
     * payment_channel : 1
     * cash_amount : 200000
     * otp_code : 362930
     * transfer_bank :
     * transfer_amount : 0
     * giro_number : 12345
     * giro_photo : Foto giro 1
     * giro_amount : 100000
     * giro_due_date : 2018-10-17T06:08:31
     * giro_number1 :
     * giro_photo1 : Foto giro 2
     * giro_amount1 : 0
     * giro_due_date1 :
     * giro_number2 :
     * giro_photo2 : Foto giro 3
     * giro_amount2 : 0
     * giro_due_date2 :
     * giro_number3 :
     * giro_photo3 : Foto giro 4
     * giro_amount3 : 0
     * giro_due_date3 :
     * giro_number4 :
     * giro_photo4 : Foto giro 5
     * giro_amount4 : 0
     * giro_due_date4 :
     * giro_photo_string : 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
     */

    @SerializedName("assignment_id")
    private String assignmentId;
    @SerializedName("invoice_code")
    private String invoiceCode;
    @SerializedName("invoice_amount")
    private String invoiceAmount;
    @SerializedName("payment_amount")
    private String paymentAmount;
    @SerializedName("payment_debt")
    private String paymentDebt;
    @SerializedName("payment_channel")
    private String paymentChannel;
    @SerializedName("cash_amount")
    private String cashAmount;
    @SerializedName("otp_code")
    private String otpCode;
    @SerializedName("transfer_bank")
    private String transferBank;
    @SerializedName("transfer_amount")
    private String transferAmount;
    @SerializedName("giro_number")
    private String giroNumber;
    @SerializedName("giro_photo")
    private String giroPhoto;
    @SerializedName("giro_amount")
    private String giroAmount;
    @SerializedName("giro_due_date")
    private String giroDueDate;
    @SerializedName("giro_number1")
    private String giroNumber1;
    @SerializedName("giro_photo1")
    private String giroPhoto1;
    @SerializedName("giro_amount1")
    private String giroAmount1;
    @SerializedName("giro_due_date1")
    private String giroDueDate1;
    @SerializedName("giro_number2")
    private String giroNumber2;
    @SerializedName("giro_photo2")
    private String giroPhoto2;
    @SerializedName("giro_amount2")
    private String giroAmount2;
    @SerializedName("giro_due_date2")
    private String giroDueDate2;
    @SerializedName("giro_number3")
    private String giroNumber3;
    @SerializedName("giro_photo3")
    private String giroPhoto3;
    @SerializedName("giro_amount3")
    private String giroAmount3;
    @SerializedName("giro_due_date3")
    private String giroDueDate3;
    @SerializedName("giro_number4")
    private String giroNumber4;
    @SerializedName("giro_photo4")
    private String giroPhoto4;
    @SerializedName("giro_amount4")
    private String giroAmount4;
    @SerializedName("giro_due_date4")
    private String giroDueDate4;
    @SerializedName("giro_photo_string")
    private String giroPhotoString;
    @SerializedName("giro_photo_string1")
    private String giroPhotoString1;
    @SerializedName("giro_photo_string2")
    private String giroPhotoString2;
    @SerializedName("giro_photo_string3")
    private String giroPhotoString3;
    @SerializedName("giro_photo_string4")
    private String giroPhotoString4;

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentDebt() {
        return paymentDebt;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public String getTransferBank() {
        return transferBank;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public String getGiroNumber() {
        return giroNumber;
    }

    public String getGiroPhoto() {
        return giroPhoto;
    }

    public String getGiroAmount() {
        return giroAmount;
    }

    public String getGiroDueDate() {
        return giroDueDate;
    }

    public String getGiroNumber1() {
        return giroNumber1;
    }

    public String getGiroPhoto1() {
        return giroPhoto1;
    }

    public String getGiroAmount1() {
        return giroAmount1;
    }

    public String getGiroDueDate1() {
        return giroDueDate1;
    }

    public String getGiroNumber2() {
        return giroNumber2;
    }

    public String getGiroPhoto2() {
        return giroPhoto2;
    }

    public String getGiroAmount2() {
        return giroAmount2;
    }

    public String getGiroDueDate2() {
        return giroDueDate2;
    }

    public String getGiroNumber3() {
        return giroNumber3;
    }

    public String getGiroPhoto3() {
        return giroPhoto3;
    }

    public String getGiroAmount3() {
        return giroAmount3;
    }

    public String getGiroDueDate3() {
        return giroDueDate3;
    }

    public String getGiroNumber4() {
        return giroNumber4;
    }

    public String getGiroPhoto4() {
        return giroPhoto4;
    }

    public String getGiroAmount4() {
        return giroAmount4;
    }

    public String getGiroDueDate4() {
        return giroDueDate4;
    }

    public String getGiroPhotoString() {
        return giroPhotoString;
    }

    public String getGiroPhotoString1() {
        return giroPhotoString1;
    }

    public String getGiroPhotoString2() {
        return giroPhotoString2;
    }

    public String getGiroPhotoString3() {
        return giroPhotoString3;
    }

    public String getGiroPhotoString4() {
        return giroPhotoString4;
    }
}
