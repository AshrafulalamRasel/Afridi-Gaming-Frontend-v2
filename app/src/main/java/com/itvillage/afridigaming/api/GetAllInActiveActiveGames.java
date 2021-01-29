package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GameResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetAllInActiveActiveGames {
    @GET("api/auth/common/show/games/inactive")
    Observable<List<GameResponse>> getAllInActiveActiveGames();
}
