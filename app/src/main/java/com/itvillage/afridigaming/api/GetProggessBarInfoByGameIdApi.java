package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GetProgessBarInfoResponse;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetProggessBarInfoByGameIdApi {
    @GET("/api/auth/user/show/join/player/games/{id}")
    Observable<GetProgessBarInfoResponse> getProgessBarInfoByGameId(@Path("id") String id);
}
