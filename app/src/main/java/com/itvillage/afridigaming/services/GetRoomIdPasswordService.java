package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.CreateNewGameApi;
import com.itvillage.afridigaming.api.ShowGameRoomDetailsApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.RoomIdAndPasswordResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GetRoomIdPasswordService {

    private final Context context;

    public GetRoomIdPasswordService(Context context) {
        this.context = context;
    }

    public Observable<RoomIdAndPasswordResponse> getRoomIdPassword(String gameId) {


        return ApiClient.getClient(context)
                .create(ShowGameRoomDetailsApi.class)
                .showGameRoomDetailsApi(gameId);
    }



}
