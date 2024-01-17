package com.radya.sfa.data.source.remote;


import androidx.lifecycle.LiveData;

/**
 * Created by Devin on 9/6/16.
 */
public interface ConnectionCallback<T>{

    void onFinishRequest(LiveData<T> r);

}
