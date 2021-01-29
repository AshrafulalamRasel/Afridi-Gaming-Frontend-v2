package com.itvillage.afridigaming.api;

import io.reactivex.Observable;

import com.itvillage.afridigaming.dto.response.SignUpResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApi {
    @POST("api/auth/user/signup")
    Observable<String> signUp(@Body RequestBody body);
}
