package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.MoneyRequestNotificationGamesApi;
import com.itvillage.afridigaming.api.UserGetProfile;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import java.util.List;

import io.reactivex.Observable;

public class MoneyRequestNotificationService {

    private final Context context;

    public MoneyRequestNotificationService(Context context) {
        this.context = context;
    }

    public Observable<List<RequestedNotificationResponse>> getMoneyRequestNotificationService() {
        return ApiClient.getClient(context).create(MoneyRequestNotificationGamesApi.class).getMoneyRequestNotificationGames();
    }


}
