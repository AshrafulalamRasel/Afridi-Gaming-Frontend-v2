package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FileUploadApi {
    @POST("api/auth/upload/image")
    Observable<String> fileUpload(@Body RequestBody body);
}
