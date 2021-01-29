package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetAllActiveGames;
import com.itvillage.afridigaming.api.GetAllGamesApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetAllGamesService {

    private final Context context;

    public GetAllGamesService(Context context) {
        this.context = context;
    }

    public Observable<List<GameResponse>> getAllGames() {
        return ApiClient.getClient(context).create(GetAllGamesApi.class).getAllGames();
    }


}
