package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.CheckUpdateResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CheckUpdateApi {
    @GET("api/auth/user/get/playsore")
    Observable<List<CheckUpdateResponse>> checkUpdateApi();
}
