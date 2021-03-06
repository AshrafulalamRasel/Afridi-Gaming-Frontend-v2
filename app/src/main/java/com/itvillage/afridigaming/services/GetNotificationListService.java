package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetNotificationListApi;
import com.itvillage.afridigaming.api.GetPaymentListApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GetNotificationResponse;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetNotificationListService {

    private final Context context;

    public GetNotificationListService(Context context) {
        this.context = context;
    }

    public Observable<List<GetNotificationResponse>> getNotificationListService() {
        return ApiClient.getClient(context).create(GetNotificationListApi.class).getNotificationList();
    }


}
