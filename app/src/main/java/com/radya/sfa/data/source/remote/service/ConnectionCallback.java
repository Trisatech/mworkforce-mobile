package com.radya.sfa.data.source.remote.service;

import com.radya.sfa.data.source.remote.ApiResponse;

/**
 * Created by aderifaldi on 08/08/2016.
 */
public interface ConnectionCallback<T> {

    void onFinishRequest(ApiResponse<T> r);

}
