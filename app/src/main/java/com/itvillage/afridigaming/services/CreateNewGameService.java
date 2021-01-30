package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.CreateNewGameApi;
import com.itvillage.afridigaming.api.UpdatePasswordApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.request.GameSetRequest;
import com.itvillage.afridigaming.dto.response.UpdatePasswordResponse;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateNewGameService {

    private final Context context;
    private final String LOGIN_REQUEST_BODY_FORMAT = "{ \"gameNumber\": \"%s\" , \"gameType\": \"%s\",\"gameName\": \"%s\",\"version\": \"%s\", \"map\": \"%s\", \"gameStatus\": \"%s\", \"roomId\": \"%s\", \"roomPassword\": \"%s\", \"totalPrize\": \"%s\", \"winnerPrize\": \"%s\", \"secondPrize\": \"%s\",\"thirdPrize\": \"%s\",\"perKillPrize\": \"%s\",\"entryFee\": \"%s\",\"gameplayOption\": \"%s\",\"gameplayStartTime\": \"%s\" }";

    public CreateNewGameService(Context context) {
        this.context = context;
    }

    public Observable<String> createGame(String gameNumber,String gameType,String gameName , String version, String map,String gameStatus,
                                                     String roomId,String roomPassword,String totalPrize,String winnerPrize,String secondPrize,String thirdPrize,String perKillPrize,String entryFee,
                                         String game_start_time,String play_device) {

        String createGame = String.format(LOGIN_REQUEST_BODY_FORMAT, gameNumber,gameType,gameName,version,map,gameStatus,roomId,roomPassword,totalPrize,winnerPrize,secondPrize,thirdPrize,perKillPrize,entryFee,play_device,game_start_time);

        return ApiClient.getClient(context)
                .create(CreateNewGameApi.class)
                .createNewGame(RequestBody.create(MediaType.parse("application/json"),createGame));
    }



}
