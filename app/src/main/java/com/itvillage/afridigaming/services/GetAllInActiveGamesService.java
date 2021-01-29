package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetAllActiveGames;
import com.itvillage.afridigaming.api.GetAllInActiveActiveGames;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetAllInActiveGamesService {

    private final Context context;

    public GetAllInActiveGamesService(Context context) {
        this.context = context;
    }

    public Observable<List<GameResponse>> getAllInActiveActiveGame() {
        return ApiClient.getClient(context).create(GetAllInActiveActiveGames.class).getAllInActiveActiveGames();
    }


}
