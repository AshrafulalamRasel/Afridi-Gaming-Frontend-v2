package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UpdateGameStatusApi;
import com.itvillage.afridigaming.api.UpdateRoomIdAndPasswordApi;
import com.itvillage.afridigaming.config.ApiClient;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateGameSatusService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT =  "{ \"gameIsActive\": \"%s\" }";

    public UpdateGameSatusService(Context context) {
        this.context = context;
    }

    public Observable<String> updateGameSatusService(String gameId,String status) {

        String updateRoomDetailsRequest = String.format(LOGIN_REQUEST_BODY_FORMAT, status);

        return ApiClient.getClient(context)
                .create(UpdateGameStatusApi.class)
                .updateGameStatusApi(gameId,RequestBody.create(MediaType.parse("application/json"), updateRoomDetailsRequest));
    }



}
