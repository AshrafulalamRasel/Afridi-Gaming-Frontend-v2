package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.LoginApi;
import com.itvillage.afridigaming.api.PostPaymentRequestApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.LoginResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PostMoneyRequestService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT = "{ \"paymentGetawayName\": \"%s\", \"amount\": \"%s\", \"lastThreeDigitOfPayableMobileNo\": \"%s\" }";

    public PostMoneyRequestService(Context context) {
        this.context = context;
    }

    public Observable<String> moneyRequest(String transactionMethod, String loadBalAmount, String loadBalAmlastThreeDigitount) {

        String loginRequestBody = String.format(LOGIN_REQUEST_BODY_FORMAT, transactionMethod, loadBalAmount,loadBalAmlastThreeDigitount);

        return ApiClient.getClient(context)
                .create(PostPaymentRequestApi.class)
                .postPaymentRequest(RequestBody.create(MediaType.parse("application/json"), loginRequestBody));
    }



}
