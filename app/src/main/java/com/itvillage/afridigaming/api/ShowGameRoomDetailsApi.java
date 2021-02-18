package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.RoomIdAndPasswordResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShowGameRoomDetailsApi {
    @GET("api/auth/user/show/all/games/room/password/{gameId}")
    Observable<RoomIdAndPasswordResponse> showGameRoomDetailsApi(@Path("gameId") String gameId);
}
