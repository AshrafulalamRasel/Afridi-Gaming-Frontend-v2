package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.RegistrationGameResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RegistrationInGameApi {
    @PUT("api/auth/registration/user/games/{gameId}")
    Observable<String> registrationInGame(@Path("gameId") String gameId, @Body RequestBody body);
}
