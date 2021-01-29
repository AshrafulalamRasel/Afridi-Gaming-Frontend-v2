package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateWithdrawRequestApi {
    @PUT("api/auth/admin/update/balance/withdraw/{id}")
    Observable<String> updateGameResultApi(@Path("id") String id);
}
