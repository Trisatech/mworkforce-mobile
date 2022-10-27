package com.radya.sfa.util;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyUtils {

    public static RequestBody getTextPlain(String value){
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static RequestBody getImage(File file){
        return RequestBody.create(MediaType.parse("image/jpg"), file);
    }

}
