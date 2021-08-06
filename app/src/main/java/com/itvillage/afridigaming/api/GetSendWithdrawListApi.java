package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetSendWithdrawListApi {
    @GET("api/auth/admin/show/withdraw/request/active")
    Observable<List<WithDrawMoneyResponse>> getActiveWithdrawListApi();
}
