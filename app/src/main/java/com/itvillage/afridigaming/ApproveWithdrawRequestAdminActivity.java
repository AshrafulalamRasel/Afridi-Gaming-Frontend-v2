package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.itvillage.afridigaming.adapter.ApprovalListAdapter;
import com.itvillage.afridigaming.adapter.WithdrawApprovalListAdapter;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;
import com.itvillage.afridigaming.services.GetInactiveWithdrawListService;
import com.itvillage.afridigaming.services.MoneyRequestNotificationService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApproveWithdrawRequestAdminActivity extends AppCompatActivity {

    private ArrayList<String> idArray = new ArrayList<>();
    private ArrayList<String> paymentGetawayNameArray = new ArrayList<>();
    private ArrayList<String> amountArray = new ArrayList<>();
    private ArrayList<String> lastThreeDigitOfPayableMobileNoArray = new ArrayList<>();
    private ArrayList<String> currentBalanceArray = new ArrayList<>();
    private ArrayList<String> updatedAtArray = new ArrayList<>();
    private ArrayList<String> userNameArray = new ArrayList<>();
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_withdraw_request_admin);
        getAllWithdrawRequestedNotification();
    }

    @SuppressLint("CheckResult")
    private void getAllWithdrawRequestedNotification() {

        GetInactiveWithdrawListService getInactiveWithdrawListService = new GetInactiveWithdrawListService(getApplicationContext());
        Observable<List<WithDrawMoneyResponse>> moneyRequestNotificationServiceOb = getInactiveWithdrawListService.getInactiveWithdrawListService();

        moneyRequestNotificationServiceOb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    for (WithDrawMoneyResponse withDrawMoneyResponse : res) {
                        idArray.add(withDrawMoneyResponse.getId());
                        paymentGetawayNameArray.add(withDrawMoneyResponse.getPaymentGetawayName());
                        amountArray.add(String.valueOf(withDrawMoneyResponse.getAmount()));
                        lastThreeDigitOfPayableMobileNoArray.add(String.valueOf(withDrawMoneyResponse.getLastThreeDigitOfPayableMobileNo()));
                        currentBalanceArray.add(String.valueOf(withDrawMoneyResponse.getCurrentBalance()));
                        updatedAtArray.add(String.valueOf(withDrawMoneyResponse.getUpdatedAt()));
                        userNameArray.add(String.valueOf(withDrawMoneyResponse.getUserName()));


                    }
                    WithdrawApprovalListAdapter adapter = new WithdrawApprovalListAdapter(this, idArray, paymentGetawayNameArray, amountArray, lastThreeDigitOfPayableMobileNoArray, currentBalanceArray, updatedAtArray,userNameArray);
                    list = (ListView) findViewById(R.id.approve_withdraw_list);
                    list.setAdapter(adapter);
                }, err -> {

                });
    }
}