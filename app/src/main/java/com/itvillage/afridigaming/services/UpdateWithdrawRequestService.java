package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UpdateGameResultApi;
import com.itvillage.afridigaming.api.UpdateWithdrawRequestApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateWithdrawRequestService {

    private final Context context;


    public UpdateWithdrawRequestService(Context context) {
        this.context = context;
    }

    public Observable<String> updateWithdrawRequestService(String id) {

        return ApiClient.getClient(context)
                .create(UpdateWithdrawRequestApi.class)
                .updateGameResultApi(id);
    }



}
