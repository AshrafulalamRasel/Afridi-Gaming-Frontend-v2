package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UpdateAllRefundApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;

public class UpdateAllRefundService {

    private final Context context;

    public UpdateAllRefundService(Context context) {
        this.context = context;
    }

    public Observable<String> updateAllRefundService(String gameId) {

        return ApiClient.getClient(context)
                .create(UpdateAllRefundApi.class)
                .updateAllRefundApi(gameId);
    }
    public Observable<String> updateAllRefundServiceById(String userId,String gameId) {

        return ApiClient.getClient(context)
                .create(UpdateAllRefundApi.class)
                .updateAllRefundByIdApi(gameId,userId);
    }


}
