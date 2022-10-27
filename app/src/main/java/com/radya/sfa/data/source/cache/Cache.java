package com.radya.sfa.data.source.cache;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RadyaLabs PC on 15/12/2017.
 */

@Entity
public class Cache {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public int id;

    @SerializedName("json")
    public String json;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
