package com.itvillage.afridigaming.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateNotificationStatusAndAddBalanceApi {
    @PUT("api/auth/admin/update/balance/approve/users/{userId}/requests/{id}")
    Observable<String> updateNotificationStatusAndAddBalanceApi(@Path("id") String id,@Path("userId") String userId,@Body RequestBody body);
}
