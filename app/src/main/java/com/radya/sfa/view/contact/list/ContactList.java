package com.radya.sfa.view.contact.list;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.view.contact.Contact;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class ContactList extends BaseModel implements Serializable {

    @SerializedName("data")
    private List<Contact> data;

    public List<Contact> getData() {
        return data;
    }

    public void setData(List<Contact> data) {
        this.data = data;
    }
}
