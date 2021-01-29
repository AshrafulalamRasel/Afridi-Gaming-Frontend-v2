package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UpdateGameStatusApi;
import com.itvillage.afridigaming.api.UpdateNotificationStatusAndAddBalanceApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateNotificationStatusAndAddBalanceService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT =  "{\"isAuthorityProcessed\":\"%s\",\"balance\":\"%s\"}";

    public UpdateNotificationStatusAndAddBalanceService(Context context) {
        this.context = context;
    }

    public Observable<String> updateNotificationStatusAndAddBalanceService(String balanceId,String userId,String balance) {

        String updateRoomDetailsRequest = String.format(LOGIN_REQUEST_BODY_FORMAT, "true",balance);

        return ApiClient.getClient(context)
                .create(UpdateNotificationStatusAndAddBalanceApi.class)
                .updateNotificationStatusAndAddBalanceApi(balanceId,userId,RequestBody.create(MediaType.parse("application/json"), updateRoomDetailsRequest));
    }



}
