package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetAllGamesApi;
import com.itvillage.afridigaming.api.GetImagesApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.ImageUrlResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetAllImagesService {

    private final Context context;

    public GetAllImagesService(Context context) {
        this.context = context;
    }

    public Observable<List<ImageUrlResponse>> getImages() {
        return ApiClient.getClient(context).create(GetImagesApi.class).getAllImages();
    }


}
