package com.radya.sfa.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by aderifaldi on 23/08/2016.
 */
public class Sha256 {

    public static String hashString(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static String hexString(String saltString) throws NoSuchAlgorithmException {
        String SHA256String = "";

        final String SHA256 = "SHA-256";
        // Create MD5 Hash
        MessageDigest digest = MessageDigest
                .getInstance(SHA256);
        digest.update(saltString.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        SHA256String = hexString.toString();
        return SHA256String;
    }

}
