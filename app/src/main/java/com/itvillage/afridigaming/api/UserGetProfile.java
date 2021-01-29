package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserGetProfile {
    @GET("api/auth/user/show/profile")
    Observable<UserCreateProfileResponse> getUserProfile();
}
