package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetPaymentListApi;
import com.itvillage.afridigaming.api.GetWithdrawListApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetPaymentListService {

    private final Context context;

    public GetPaymentListService(Context context) {
        this.context = context;
    }

    public Observable<List<WithDrawMoneyResponse>> getWithdrawListService() {
        return ApiClient.getClient(context).create(GetPaymentListApi.class).getPaymentListApi();
    }


}
