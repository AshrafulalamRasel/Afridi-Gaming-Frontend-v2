package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.CreateNewGameApi;
import com.itvillage.afridigaming.api.GetAllActiveGames;
import com.itvillage.afridigaming.api.UserGetProfile;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetAllActiveGamesService {

    private final Context context;

    public GetAllActiveGamesService(Context context) {
        this.context = context;
    }

    public Observable<List<GameResponse>> getAllActiveGame() {
        return ApiClient.getClient(context).create(GetAllActiveGames.class).getAllActiveGames();
    }


}
