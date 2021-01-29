package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.CreateNewGameApi;
import com.itvillage.afridigaming.api.WithdrawMoneyRequestApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WithdrawMoneyRequestService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT = "{ \"paymentGetawayName\": \"%s\" , \"amount\": \"%s\",\"lastThreeDigitOfPayableMobileNo\": \"%s\" }";

    public WithdrawMoneyRequestService(Context context) {
        this.context = context;
    }

    public Observable<String> withdrawMoneyRequestApi(String paymentGetawayName,String amount,String lastThreeDigitOfPayableMobileNo ) {

        String withdrawMoneyRequestApi = String.format(LOGIN_REQUEST_BODY_FORMAT, paymentGetawayName,amount,lastThreeDigitOfPayableMobileNo);

        return ApiClient.getClient(context)
                .create(WithdrawMoneyRequestApi.class)
                .withdrawMoneyRequestApi(RequestBody.create(MediaType.parse("application/json"),withdrawMoneyRequestApi));
    }



}
