package com.radya.sfa.helper.image;

import android.util.Log;

import java.security.MessageDigest;

public class CipherUtil {

    public static String hash(String key, String plain) {
        String result = "";
        result = result + (key.substring(0, 4));
        result = result + plain;
        result = result + (key.substring(4));

        Log.i("HAE", result);

        return sha256(result);
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}