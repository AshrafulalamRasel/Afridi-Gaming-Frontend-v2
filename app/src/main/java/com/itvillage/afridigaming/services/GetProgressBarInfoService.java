package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.DeleteImageSliderApi;
import com.itvillage.afridigaming.api.GetProggessBarInfoByGameIdApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GetProgessBarInfoResponse;

import io.reactivex.Observable;

public class GetProgressBarInfoService {

    private final Context context;

    public GetProgressBarInfoService(Context context) {
        this.context = context;
    }

    public Observable<GetProgessBarInfoResponse> getProgessBarInfoByGameId(String id) {
        return ApiClient.getClient(context).create(GetProggessBarInfoByGameIdApi.class).getProgessBarInfoByGameId(id);
    }


}
