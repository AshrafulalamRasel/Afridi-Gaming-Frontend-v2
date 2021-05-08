package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GameResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeleteImageSliderApi {
    @DELETE("/api/auth/delete/{id}")
    Observable<String> deleteImages(@Path("id") String id);
}
