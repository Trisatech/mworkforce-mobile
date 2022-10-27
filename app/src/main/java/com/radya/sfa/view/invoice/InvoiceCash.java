package com.radya.sfa.view.invoice;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class InvoiceCash {

    @SerializedName("cash_amount")
    private long cash_amount;

    public long getCash_amount() {
        return cash_amount;
    }

    public void setCash_amount(long cash_amount) {
        this.cash_amount = cash_amount;
    }

    public InvoiceCash(long cash_amount) {
        this.cash_amount = cash_amount;
    }

}
