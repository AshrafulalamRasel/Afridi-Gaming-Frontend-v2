package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotificationApi {
    @POST("api/auth/notification/send")
    Observable<String> postNotification(@Body RequestBody body);
}
