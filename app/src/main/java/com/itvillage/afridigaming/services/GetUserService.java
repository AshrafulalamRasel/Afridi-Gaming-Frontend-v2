package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UserCreateProfile;
import com.itvillage.afridigaming.api.UserGetProfile;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GetUserService {

    private final Context context;

    public GetUserService(Context context) {
        this.context = context;
    }

    public Observable<UserCreateProfileResponse> getUserProfile() {
        return ApiClient.getClient(context).create(UserGetProfile.class).getUserProfile();
    }


}
