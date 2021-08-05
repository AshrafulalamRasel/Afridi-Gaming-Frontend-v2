package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.ApprovedMoneyRequestApi;
import com.itvillage.afridigaming.api.MoneyRequestNotificationGamesApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;

import java.util.List;

import io.reactivex.Observable;

public class ApprovedMoneyRequestService {

    private final Context context;

    public ApprovedMoneyRequestService(Context context) {
        this.context = context;
    }

    public Observable<List<RequestedNotificationResponse>> getApprovedMoneyRequest() {
        return ApiClient.getClient(context).create(ApprovedMoneyRequestApi.class).getApprovedMoneyRequest();
    }


}
