package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UpdateGameResultApi;
import com.itvillage.afridigaming.api.UpdateGameStatusApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateGameResultService {

    private final Context context;
    private final String REQUEST_BODY_FORMAT =  "{ \"perKill\": \"%s\" , \"prize\": \"%s\"}";

    public UpdateGameResultService(Context context) {
        this.context = context;
    }

    public Observable<String> updateGameResultService(String gameId, String userId, String squadPrize,String numberOfKill) {

        String updateRoomDetailsRequest = String.format(REQUEST_BODY_FORMAT, numberOfKill,squadPrize);

        return ApiClient.getClient(context)
                .create(UpdateGameResultApi.class)
                .updateGameResultApi(userId,gameId,RequestBody.create(MediaType.parse("application/json"), updateRoomDetailsRequest));
    }



}
