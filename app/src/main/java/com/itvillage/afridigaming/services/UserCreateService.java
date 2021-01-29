package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.LoginApi;
import com.itvillage.afridigaming.api.UserCreateProfile;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserCreateService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT = "{ \"firstName\": \"%s\", \"lastName\": \"%s\",\"mobileNo\": \"%s\"}";

    public UserCreateService(Context context) {
        this.context = context;
    }

    public Observable<String> createUser(String firstName, String lastName,String mobileNo) {

        String createUserResponse = String.format(LOGIN_REQUEST_BODY_FORMAT, firstName, lastName,mobileNo);

        return ApiClient.getClient(context)
                .create(UserCreateProfile.class)
                .userCreate(RequestBody.create(MediaType.parse("application/json"), createUserResponse));
    }



}
