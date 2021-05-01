package com.itvillage.afridigaming.api;

import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.PaymentDetailsResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetPaymentDetailsListApi {
    @GET("api/auth/show/history")
    Observable<List<PaymentDetailsResponse>> getPaymentReportsList();
}
