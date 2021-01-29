package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoneyRequestNotificationGamesApi {
    @GET("api/auth/admin/show/balance/request/inactive")
    Observable<List<RequestedNotificationResponse>> getMoneyRequestNotificationGames();
}
