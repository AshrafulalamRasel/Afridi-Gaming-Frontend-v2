package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.DeleteImageSliderApi;
import com.itvillage.afridigaming.api.GetAllActiveGames;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;

import java.util.List;

import io.reactivex.Observable;

public class DeleteImageService {

    private final Context context;

    public DeleteImageService(Context context) {
        this.context = context;
    }

    public Observable<String> deleteImages(String id) {
        return ApiClient.getClient(context).create(DeleteImageSliderApi.class).deleteImages(id);
    }


}
