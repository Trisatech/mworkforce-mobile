package com.radya.sfa.data.source.remote;

import com.radya.sfa.view.report.ModelGoogleDirection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aderifaldi on 08/08/2016.
 */
public interface ApiServiceDirection {

    /**
     * END POINT
     */
    String JSON = "json";

    /**
     * ANALYZE
     */
    @GET(JSON)
    Call<ModelGoogleDirection> getDirection(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

}
