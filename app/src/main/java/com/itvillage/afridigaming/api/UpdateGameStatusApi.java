package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateGameStatusApi {
    @PUT("api/auth/admin/change/staus/games/{gameId}")
    Observable<String> updateGameStatusApi(@Path("gameId") String gameId,@Body RequestBody body);
}
