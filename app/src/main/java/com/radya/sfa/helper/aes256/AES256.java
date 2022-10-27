package com.radya.sfa.helper.aes256;

import com.radya.sfa.Constant;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by aderifaldi on 14/12/2016.
 */

public class AES256 {

    private static String newKey = Constant.Key.AES.A + Constant.Key.AES.R;
    private static byte[] w = newKey.getBytes();

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(w, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(w, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getEncryptedString(String str) {
        final String encryptedStr = AES256.encrypt(str);
        return encryptedStr;
    }
    public static String getDecriyptedString(String str) {
        final String decryptedStr = AES256.decrypt(str);
        return decryptedStr;
    }
}
