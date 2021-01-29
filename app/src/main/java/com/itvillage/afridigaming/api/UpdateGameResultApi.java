package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateGameResultApi {
    @PUT("api/auth/admin/update/balance/users/{userId}/game/{gameId}")
    Observable<String> updateGameResultApi(@Path("userId") String userId,@Path("gameId") String gameId,@Body RequestBody body);
}
