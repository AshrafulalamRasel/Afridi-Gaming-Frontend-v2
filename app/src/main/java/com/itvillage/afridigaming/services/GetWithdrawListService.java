package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetAllActiveGames;
import com.itvillage.afridigaming.api.GetWithdrawListApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetWithdrawListService {

    private final Context context;

    public GetWithdrawListService(Context context) {
        this.context = context;
    }

    public Observable<List<WithDrawMoneyResponse>> getWithdrawListService() {
        return ApiClient.getClient(context).create(GetWithdrawListApi.class).getWithdrawList();
    }


}
