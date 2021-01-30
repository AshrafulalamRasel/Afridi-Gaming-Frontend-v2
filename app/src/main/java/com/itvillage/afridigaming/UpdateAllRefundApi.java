package com.itvillage.afridigaming;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateAllRefundApi {
    @PUT("api/auth/games/{gameId}/players/refund")
    Observable<String> updateAllRefundApi(@Path("gameId") String gameId);

    @PUT("api/auth/games/{gameId}/players/{id}/refund")
    Observable<String> updateAllRefundByIdApi(@Path("gameId") String gameId,@Path("id") String userId);
}
