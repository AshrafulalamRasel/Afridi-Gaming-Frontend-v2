package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.CheckUpdateApi;
import com.itvillage.afridigaming.api.GetAllActiveGames;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.CheckUpdateResponse;
import com.itvillage.afridigaming.dto.response.GameResponse;

import java.util.List;

import io.reactivex.Observable;

public class CheckUpdateService {

    private final Context context;

    public CheckUpdateService(Context context) {
        this.context = context;
    }

    public Observable<List<CheckUpdateResponse>> checkUpdate() {
        return ApiClient.getClient(context).create(CheckUpdateApi.class).checkUpdateApi();
    }


}
