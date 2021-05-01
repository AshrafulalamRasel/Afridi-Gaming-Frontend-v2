package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.itvillage.afridigaming.adapter.ResultListAdapter;
import com.itvillage.afridigaming.adapter.WithdrawListAdapter;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;
import com.itvillage.afridigaming.services.GetPaymentListService;
import com.itvillage.afridigaming.services.GetWithdrawListService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentHistoryActivity extends AppCompatActivity {

    private ArrayList<String> paymentGetawayNameArray = new ArrayList<>();
    private ArrayList<String> amountArray = new ArrayList<>();
    private ArrayList<String> acNoOfPayableMobileNoArray = new ArrayList<>();
    private ArrayList<String> userNameArray = new ArrayList<>();
    private ArrayList<String> currentBalanceArray = new ArrayList<>();
    private ArrayList<String> updatedAtArray = new ArrayList<>();
    private ArrayList<Boolean> statusArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        setContentView(R.layout.activity_withdraw_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWithdrawList();
    }
    @SuppressLint("CheckResult")
    private void getWithdrawList() {
        GetPaymentListService getWithdrawListService = new GetPaymentListService(this);
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
                        currentBalanceArray.add("");
                        statusArray.add(withDrawMoneyResponse.isAuthorityProcessed());
                        updatedAtArray.add(String.valueOf(withDrawMoneyResponse.getUpdatedAt()));


                    }
                    WithdrawListAdapter withdrawListAdapter = new WithdrawListAdapter(this, paymentGetawayNameArray, amountArray,acNoOfPayableMobileNoArray,userNameArray,currentBalanceArray,updatedAtArray,statusArray);
                    ListView withdraw_list = findViewById(R.id.withdraw_list);
                    withdraw_list.setAdapter(withdrawListAdapter);

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;

    }
}