package com.radya.sfa.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.helper.SSLPinner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RadyaLabs PC on 08/02/2018.
 */

public class ApiRequest {

    private String baseUrl = Constant.Api.BASE_URL;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ApiRequest(){
    }

    public ApiService call(boolean useAccessToken) {

        Gson gson = new GsonBuilder()
                .setDateFormat(Constant.DateFormat.SERVER)
                .create();

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //IF USE SSL PINNING
        //addPinnedSSL(builder);

        builder.readTimeout(Constant.Api.TIMEOUT, TimeUnit.MINUTES);
        builder.connectTimeout(Constant.Api.TIMEOUT, TimeUnit.MINUTES);
        builder.addInterceptor(logInterceptor);

        if (useAccessToken) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header(Constant.Api.Params.AUTHORIZATION, PrefManager.getString(Constant.Preference.User.AUTH_TOKEN));
                    Log.d(Constant.Preference.User.AUTH_TOKEN, PrefManager.getString(Constant.Preference.User.AUTH_TOKEN));
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();

        return retrofit.create(ApiService.class);
    }

    private void addPinnedSSL(OkHttpClient.Builder builder){
        Context context = MyApplication.getInstance();
        SSLPinner sslPinner = new SSLPinner(context);

        builder.sslSocketFactory(sslPinner.getPinnedCertSslSocketFactory());
    }

}
