package com.radya.sfa.view.invoice.master;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class InvoiceBank {

    @SerializedName("bank_code")
    private String bank_code;
    @SerializedName("bank_name")
    private String bank_name;

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public InvoiceBank(String bank_code, String bank_name) {
        this.bank_code = bank_code;
        this.bank_name = bank_name;
    }

}
