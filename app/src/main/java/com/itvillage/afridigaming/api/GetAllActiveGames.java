package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetAllActiveGames {
    @GET("api/auth/common/show/games/active")
    Observable<List<GameResponse>> getAllActiveGames();
}
