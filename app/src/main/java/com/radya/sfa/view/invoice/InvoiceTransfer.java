package com.radya.sfa.view.invoice;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class InvoiceTransfer {

    @SerializedName("bank")
    private String bank;
    @SerializedName("bank_code")
    private String bank_code;
    @SerializedName("transfer_amount")
    private long transfer_amount;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public long getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(long transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public InvoiceTransfer(String bank, String bank_code, long transfer_amount) {
        this.bank = bank;
        this.bank_code = bank_code;
        this.transfer_amount = transfer_amount;
    }

}
