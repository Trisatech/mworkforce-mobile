package com.radya.sfa.view.invoice;

import com.google.gson.annotations.SerializedName;

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class InvoiceGiro {

    @SerializedName("no_giro")
    private String no_giro;
    @SerializedName("giro_amount")
    private long giro_amount;
    @SerializedName("giro_photo")
    private File giro_photo;

    public String getNo_giro() {
        return no_giro;
    }

    public void setNo_giro(String no_giro) {
        this.no_giro = no_giro;
    }

    public long getGiro_amount() {
        return giro_amount;
    }

    public void setGiro_amount(long giro_amount) {
        this.giro_amount = giro_amount;
    }

    public File getGiro_photo() {
        return giro_photo;
    }

    public void setGiro_photo(File giro_photo) {
        this.giro_photo = giro_photo;
    }

    public InvoiceGiro(String no_giro, long giro_amount, File giro_photo) {
        this.no_giro = no_giro;
        this.giro_amount = giro_amount;
        this.giro_photo = giro_photo;
    }
}
