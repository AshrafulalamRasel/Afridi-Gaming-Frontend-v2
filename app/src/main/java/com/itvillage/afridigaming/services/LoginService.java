package com.itvillage.afridigaming.services;

import android.content.Context;


import com.itvillage.afridigaming.api.LoginApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.LoginResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT = "{ \"username\": \"%s\", \"password\": \"%s\" }";

    public LoginService(Context context) {
        this.context = context;
    }

    public Observable<LoginResponse> login(String username, String password) {

        String loginRequestBody = String.format(LOGIN_REQUEST_BODY_FORMAT, username, password);

        return ApiClient.getClient(context)
                .create(LoginApi.class)
                .signIn(RequestBody.create(MediaType.parse("application/json"), loginRequestBody));
    }



}
