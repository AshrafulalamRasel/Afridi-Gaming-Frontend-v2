package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserCreateProfile {
    @POST("api/auth/user/create/profile")
    Observable<String> userCreate(@Body RequestBody body);
}
