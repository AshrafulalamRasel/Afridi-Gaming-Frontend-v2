package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetPaymentListApi {
    @GET("api/auth/user/show/balance/request/allTransaction")
    Observable<List<WithDrawMoneyResponse>> getPaymentListApi();
}
