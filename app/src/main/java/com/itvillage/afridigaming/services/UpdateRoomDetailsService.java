package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.UpdatePasswordApi;
import com.itvillage.afridigaming.api.UpdateRoomIdAndPasswordApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.UpdatePasswordResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateRoomDetailsService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT =  "{ \"roomId\": \"%s\", \"roomPassword\": \"%s\" }";

    public UpdateRoomDetailsService(Context context) {
        this.context = context;
    }

    public Observable<String> updateRoomDetailsService(String gameId,String roomId,String roomPassword) {

        String updateRoomDetailsRequest = String.format(LOGIN_REQUEST_BODY_FORMAT, roomId,roomPassword);

        return ApiClient.getClient(context)
                .create(UpdateRoomIdAndPasswordApi.class)
                .updateRoomIdAndPasswordApi(gameId,RequestBody.create(MediaType.parse("application/json"), updateRoomDetailsRequest));
    }



}
