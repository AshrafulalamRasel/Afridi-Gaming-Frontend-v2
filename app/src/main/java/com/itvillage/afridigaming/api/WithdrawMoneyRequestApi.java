package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WithdrawMoneyRequestApi {
    @POST("api/auth/user/balance/withdraw/requested")
    Observable<String> withdrawMoneyRequestApi(@Body RequestBody body);
}
