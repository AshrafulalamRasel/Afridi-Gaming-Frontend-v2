package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.ImageUrlResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetImagesApi {
    @GET("api/auth/getUrl")
    Observable<List<ImageUrlResponse>> getAllImages();
}
