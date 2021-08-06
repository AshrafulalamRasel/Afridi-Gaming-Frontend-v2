package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetInactiveWithdrawListApi;
import com.itvillage.afridigaming.api.GetSendWithdrawListApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetSendWithdrawListService {

    private final Context context;

    public GetSendWithdrawListService(Context context) {
        this.context = context;
    }

    public Observable<List<WithDrawMoneyResponse>> getActiveWithdrawListApi() {
        return ApiClient.getClient(context).create(GetSendWithdrawListApi.class).getActiveWithdrawListApi();
    }


}
