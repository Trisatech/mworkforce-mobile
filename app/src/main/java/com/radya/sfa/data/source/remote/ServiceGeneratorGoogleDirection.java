package com.radya.sfa.data.source.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aderifaldi on 08/08/2016.
 */
public class ServiceGeneratorGoogleDirection {

    static final String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";
    static final int TIMEOUT = 60;
    static Context ctx;

    private ServiceGeneratorGoogleDirection() {
    }

    public static <S> S createService(Class<S> serviceClass, Context context) {

        ctx = context;
        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();

        return builder.create(serviceClass);

    }

}
