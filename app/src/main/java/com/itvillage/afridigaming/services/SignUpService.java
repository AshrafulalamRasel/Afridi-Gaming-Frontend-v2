package com.itvillage.afridigaming.services;

import android.content.Context;
import android.util.Log;


import com.itvillage.afridigaming.api.SignUpApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.SignUpResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignUpService {

    private final Context context;

    private final String SIGN_UP_REQUEST_BODY_FORMAT = "{\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}";

    public SignUpService(Context context) {
        this.context = context;
    }


    public Observable<String> createPatientWithSignUP(String userName,
                                                              String email,
                                                              String password) {

        String signUpRequestBody = String.format(SIGN_UP_REQUEST_BODY_FORMAT, userName, email, password);
        Log.d("Body", signUpRequestBody);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), signUpRequestBody);

        return ApiClient.getClient(context).create(SignUpApi.class).signUp(requestBody);


    }

}
