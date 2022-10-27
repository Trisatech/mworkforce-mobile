package com.radya.sfa.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.radya.sfa.MyApplication;
import com.radya.sfa.data.source.remote.ConnectionManager;

public class NetworkUtils {

    public static ConnectionManager getConnectionManager(){
        return new ConnectionManager<>();
    }

    public static com.radya.sfa.data.source.remote.service.ConnectionManager getConnectionManagerService(){
        return new com.radya.sfa.data.source.remote.service.ConnectionManager<>();
    }

    public static boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
