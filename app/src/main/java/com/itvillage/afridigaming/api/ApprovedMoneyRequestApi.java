package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApprovedMoneyRequestApi {
    @GET("api/auth/get/money/request/byUsers")
    Observable<List<RequestedNotificationResponse>> getApprovedMoneyRequest();
}
