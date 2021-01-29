package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetInactiveWithdrawListApi;
import com.itvillage.afridigaming.api.GetWithdrawListApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetInactiveWithdrawListService {

    private final Context context;

    public GetInactiveWithdrawListService(Context context) {
        this.context = context;
    }

    public Observable<List<WithDrawMoneyResponse>> getInactiveWithdrawListService() {
        return ApiClient.getClient(context).create(GetInactiveWithdrawListApi.class).getInactiveWithdrawListApi();
    }


}
