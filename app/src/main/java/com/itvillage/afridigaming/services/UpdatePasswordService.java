package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.LoginApi;
import com.itvillage.afridigaming.api.UpdatePasswordApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.dto.response.UpdatePasswordResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdatePasswordService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT = "{ \"password\": \"%s\" }";

    public UpdatePasswordService(Context context) {
        this.context = context;
    }

    public Observable<String> updatePassword(String password) {

        String updatePasswordResponse = String.format(LOGIN_REQUEST_BODY_FORMAT, password);

        return ApiClient.getClient(context)
                .create(UpdatePasswordApi.class)
                .updatePassword(RequestBody.create(MediaType.parse("application/json"), updatePasswordResponse));
    }



}
