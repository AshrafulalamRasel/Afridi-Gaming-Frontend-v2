package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.itvillage.afridigaming.adapter.PalyedGameListAdapter;
import com.itvillage.afridigaming.adapter.ResultListAdapter;
import com.itvillage.afridigaming.adapter.WithdrawListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;
import com.itvillage.afridigaming.services.GetAllInActiveGamesService;
import com.itvillage.afridigaming.services.GetWithdrawListService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WithdrawHistoryActivity extends AppCompatActivity {

    private ArrayList<String> paymentGetawayNameArray = new ArrayList<>();
    private ArrayList<String> amountArray = new ArrayList<>();
    private ArrayList<String> acNoOfPayableMobileNoArray = new ArrayList<>();
    private ArrayList<String> userNameArray = new ArrayList<>();
    private ArrayList<String> currentBalanceArray = new ArrayList<>();
    private ArrayList<String> updatedAtArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_history);
        getWithdrawList();

    }

    @SuppressLint("CheckResult")
    private void getWithdrawList() {
        GetWithdrawListService getWithdrawListService = new GetWithdrawListService(this);
        Observable<List<WithDrawMoneyResponse>> listObservable =
                getWithdrawListService.getWithdrawListService();


        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(withDrawMoneysResponse -> {
                    for (WithDrawMoneyResponse withDrawMoneyResponse : withDrawMoneysResponse) {
                        paymentGetawayNameArray.add(withDrawMoneyResponse.getPaymentGetawayName());
                        amountArray.add(String.valueOf(withDrawMoneyResponse.getAmount()));
                        acNoOfPayableMobileNoArray.add(withDrawMoneyResponse.getLastThreeDigitOfPayableMobileNo());
                        userNameArray.add(withDrawMoneyResponse.getUserName());
                        currentBalanceArray.add("Current Balance: "+ String.valueOf(withDrawMoneyResponse.getCurrentBalance()));
                        updatedAtArray.add(String.valueOf(withDrawMoneyResponse.getUpdatedAt()));


                    }
                    WithdrawListAdapter withdrawListAdapter = new WithdrawListAdapter(this, paymentGetawayNameArray, amountArray,acNoOfPayableMobileNoArray,userNameArray,currentBalanceArray,updatedAtArray);
                    ListView withdraw_list = findViewById(R.id.withdraw_list);
                    withdraw_list.setAdapter(withdrawListAdapter);

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}