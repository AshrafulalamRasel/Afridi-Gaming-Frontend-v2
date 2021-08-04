package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.adapter.ApprovalListAdapter;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;
import com.itvillage.afridigaming.services.MoneyRequestNotificationService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoneyApprovalActivity extends AppCompatActivity {
    ListView list;
    private Button addGameBut;

    ArrayList<String> userIdList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> paymentGetwayList = new ArrayList<>();
    ArrayList<String> amountList = new ArrayList<>();
    ArrayList<String> mobileLastDigit = new ArrayList<>();
    ArrayList<String> balanceIdList = new ArrayList<>();
    ArrayList<String> dateTimeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_approval);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAllRequestedNotification();

    }

    @SuppressLint("CheckResult")
    private void getAllRequestedNotification() {

        MoneyRequestNotificationService moneyRequestNotificationService = new MoneyRequestNotificationService(getApplicationContext());
        Observable<List<RequestedNotificationResponse>> moneyRequestNotificationServiceOb = moneyRequestNotificationService.getMoneyRequestNotificationService();

        moneyRequestNotificationServiceOb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    for (RequestedNotificationResponse requestedNotificationResponse : res) {
                        userIdList.add(requestedNotificationResponse.getUserId());
                        nameList.add(requestedNotificationResponse.getName());
                        paymentGetwayList.add(requestedNotificationResponse.getPaymentGetawayName());
                        amountList.add(String.valueOf(requestedNotificationResponse.getAmount()));
                        balanceIdList.add(String.valueOf(requestedNotificationResponse.getId()));
                        mobileLastDigit.add(String.valueOf(requestedNotificationResponse.getLastThreeDigitOfPayableMobileNo()));
                        dateTimeList.add(String.valueOf(requestedNotificationResponse.getCreatedAt()));


                    }
                    ApprovalListAdapter adapter = new ApprovalListAdapter(this, userIdList,
                            nameList, paymentGetwayList, amountList, mobileLastDigit,
                            balanceIdList,dateTimeList);
                    list = (ListView) findViewById(R.id.approval_list);
                    list.setAdapter(adapter);
                }, err -> {

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