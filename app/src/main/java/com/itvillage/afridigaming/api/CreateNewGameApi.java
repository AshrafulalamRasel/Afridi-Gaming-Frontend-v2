package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.request.GameSetRequest;
import com.itvillage.afridigaming.dto.response.UpdatePasswordResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface CreateNewGameApi {
    @POST("api/auth/admin/create/game")
    Observable<String> createNewGame(@Body RequestBody body);
}
