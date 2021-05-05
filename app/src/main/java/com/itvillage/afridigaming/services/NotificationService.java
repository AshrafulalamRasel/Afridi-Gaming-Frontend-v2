package com.itvillage.afridigaming.services;

import android.content.Context;
import android.util.Log;

import com.itvillage.afridigaming.api.NotificationApi;
import com.itvillage.afridigaming.api.SignUpApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NotificationService {

    private final Context context;

    private final String SIGN_UP_REQUEST_BODY_FORMAT = "{\"notificationSubject\":\"%s\",\"notificationBody\":\"%s\"}";

    public NotificationService(Context context) {
        this.context = context;
    }


    public Observable<String> postNotification(String subject,String body) {

        String signUpRequestBody = String.format(SIGN_UP_REQUEST_BODY_FORMAT, subject, body);
        Log.d("Body", signUpRequestBody);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), signUpRequestBody);

        return ApiClient.getClient(context).create(NotificationApi.class).postNotification(requestBody);


    }

}
