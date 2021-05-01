package com.itvillage.afridigaming.services;

import android.content.Context;

import com.itvillage.afridigaming.api.GetAllInActiveActiveGames;
import com.itvillage.afridigaming.api.GetPaymentDetailsListApi;
import com.itvillage.afridigaming.config.ApiClient;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.PaymentDetailsResponse;

import java.util.List;

import io.reactivex.Observable;

public class GetPaymentReportListService {

    private final Context context;

    public GetPaymentReportListService(Context context) {
        this.context = context;
    }

    public Observable<List<PaymentDetailsResponse>> getPaymentReportsList() {
        return ApiClient.getClient(context).create(GetPaymentDetailsListApi.class).getPaymentReportsList();
    }


}
