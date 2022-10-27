package com.radya.sfa.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Base64Utils {

    public static String getBase64ofImage(File file){
        String filePathGiroPhoto = file.getPath();
        Bitmap bitmapGiroPhoto = BitmapFactory.decodeFile(filePathGiroPhoto);
        ByteArrayOutputStream baosGiroPhoto = new ByteArrayOutputStream();
        bitmapGiroPhoto.compress(Bitmap.CompressFormat.JPEG, 100, baosGiroPhoto); //bm is the bitmap object
        byte[] byteGiroPhoto = baosGiroPhoto.toByteArray();

        return Base64.encodeBytes(byteGiroPhoto);
    }

    public static Bitmap getBitmapOfBase64(String base64String){
        byte[] decodedStringGiroPhoto = null;
        try {
            decodedStringGiroPhoto = Base64.decode(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeByteArray(decodedStringGiroPhoto, 0, decodedStringGiroPhoto.length);
    }

}
