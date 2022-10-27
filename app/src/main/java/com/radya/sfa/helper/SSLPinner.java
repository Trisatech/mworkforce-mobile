package com.radya.sfa.helper;

import android.content.Context;
import android.util.Log;

import com.radya.sfa.Constant;
import com.radya.sfa.R;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by aderifaldi on 2018-01-18.
 */

public class SSLPinner {

    private Context context;

    public SSLPinner(Context c) {
        this.context = c;
    }

    public SSLSocketFactory getPinnedCertSslSocketFactory() {
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.certs);
            String TRUST = Constant.Key.SSL.TRUST;
            trusted.load(in, TRUST.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            Log.e("MyApp", e.getMessage(), e);
        }
        return null;
    }

}
