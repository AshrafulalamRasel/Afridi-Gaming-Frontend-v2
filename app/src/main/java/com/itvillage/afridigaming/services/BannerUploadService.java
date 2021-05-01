package com.itvillage.afridigaming.services;

import android.content.Context;
import android.util.Log;

import com.itvillage.afridigaming.api.FileUploadApi;
import com.itvillage.afridigaming.api.SignUpApi;
import com.itvillage.afridigaming.config.ApiClient;

import java.io.File;
import java.util.Random;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BannerUploadService {

    private final Context context;


    public BannerUploadService(Context context) {
        this.context = context;
    }


    public Observable<String> uplaoad(File filePath,
                                      String description, String fileName) {
        Random rand = new Random(); //instance of random class
        int upperbound = 25;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName+int_random+".jpg", RequestBody.create(MediaType.parse("multipart/form-data"), filePath))
                .addFormDataPart("webUrl", description)
                .build();
        return ApiClient.getClient(context).create(FileUploadApi.class).fileUpload(requestBody);


    }

}
