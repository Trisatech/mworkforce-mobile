
package com.radya.sfa.data.source.sync;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class Sync {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    public int id;

    @SerializedName("assignment_id")
    public String assignment_id;

    @SerializedName("json")
    public String json;

    @SerializedName("type")
    public String type;

    @SerializedName("post_type")
    public int post_type;

}
