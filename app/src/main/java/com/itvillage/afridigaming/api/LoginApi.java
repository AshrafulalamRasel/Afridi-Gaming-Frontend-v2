package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.dto.response.SignUpResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface LoginApi {
    @POST("api/auth/common/signin")
    Observable<LoginResponse> signIn(@Body RequestBody body);

    @PUT("api/auth/common/password/reset")
    Observable<String> resetPassword(@Body RequestBody body);
}
