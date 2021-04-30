package com.itvillage.afridigaming.services;

import android.content.Context;
import android.util.Log;

import com.itvillage.afridigaming.api.FileUploadApi;
import com.itvillage.afridigaming.api.SignUpApi;
import com.itvillage.afridigaming.config.ApiClient;

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


    public Observable<String> uplaoad(String filePath,
                                                              String description,String fileName) {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(MediaType.parse("image/png"), filePath))
                .addFormDataPart("result", description)
                .build();
        return ApiClient.getClient(context).create(FileUploadApi.class).fileUpload(requestBody);


    }

}
