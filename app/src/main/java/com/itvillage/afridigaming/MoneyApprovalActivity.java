package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.adapter.ApprovalListAdapter;
import com.itvillage.afridigaming.adapter.ApprovalMoneyRequestAdapter;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;
import com.itvillage.afridigaming.services.ApprovedMoneyRequestService;
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
    ArrayList<String> balanceStatusList = new ArrayList<>();
    private  String[] country = { "Payment Received", "Payment Send"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_approval);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner action_type = (Spinner) findViewById(R.id.action_type);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        action_type.setAdapter(aa);

        action_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    getAllRequestedNotification();
                }else{
                    getApprovedMoneyRequest();
                    Toast.makeText(getApplicationContext(), country[i], Toast.LENGTH_LONG).show();
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @SuppressLint("CheckResult")
    private void getApprovedMoneyRequest() {
        ApprovedMoneyRequestService approvedMoneyRequestService= new ApprovedMoneyRequestService(getApplicationContext());
        Observable<List<RequestedNotificationResponse>> moneyRequestNotificationServiceOb = approvedMoneyRequestService.getApprovedMoneyRequest();

        moneyRequestNotificationServiceOb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    userIdList = new ArrayList<>();
                    nameList = new ArrayList<>();
                    paymentGetwayList = new ArrayList<>();
                    amountList = new ArrayList<>();
                    mobileLastDigit = new ArrayList<>();
                    balanceIdList = new ArrayList<>();
                    balanceStatusList = new ArrayList<>();
                    for (RequestedNotificationResponse requestedNotificationResponse : res) {
                        userIdList.add(requestedNotificationResponse.getUserId());
                        nameList.add(requestedNotificationResponse.getName());
                        paymentGetwayList.add(requestedNotificationResponse.getPaymentGetawayName());
                        amountList.add(String.valueOf(requestedNotificationResponse.getAmount()));
                        balanceIdList.add(String.valueOf(requestedNotificationResponse.getId()));
                        mobileLastDigit.add(String.valueOf(requestedNotificationResponse.getLastThreeDigitOfPayableMobileNo()));
                        balanceStatusList.add(String.valueOf(requestedNotificationResponse.getBalanceStatus()));


                    }
                    ApprovalMoneyRequestAdapter adapter = new ApprovalMoneyRequestAdapter(this, userIdList,
                            nameList, paymentGetwayList, amountList, mobileLastDigit,
                            balanceIdList,balanceStatusList);
                    list = (ListView) findViewById(R.id.approval_list);
                    list.setAdapter(adapter);
                }, err -> {

                });
    }

    @SuppressLint("CheckResult")
    private void getAllRequestedNotification() {

        MoneyRequestNotificationService moneyRequestNotificationService = new MoneyRequestNotificationService(getApplicationContext());
        Observable<List<RequestedNotificationResponse>> moneyRequestNotificationServiceOb = moneyRequestNotificationService.getMoneyRequestNotificationService();

        moneyRequestNotificationServiceOb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    userIdList = new ArrayList<>();
                    nameList = new ArrayList<>();
                    paymentGetwayList = new ArrayList<>();
                    amountList = new ArrayList<>();
                    mobileLastDigit = new ArrayList<>();
                    balanceIdList = new ArrayList<>();
                    dateTimeList = new ArrayList<>();
                    for (RequestedNotificationResponse requestedNotificationResponse : res) {
                        if(requestedNotificationResponse.getBalanceStatus() == null) {
                            userIdList.add(requestedNotificationResponse.getUserId());
                            nameList.add(requestedNotificationResponse.getName());
                            paymentGetwayList.add(requestedNotificationResponse.getPaymentGetawayName());
                            amountList.add(String.valueOf(requestedNotificationResponse.getAmount()));
                            balanceIdList.add(String.valueOf(requestedNotificationResponse.getId()));
                            mobileLastDigit.add(String.valueOf(requestedNotificationResponse.getLastThreeDigitOfPayableMobileNo()));
                            dateTimeList.add(String.valueOf(requestedNotificationResponse.getCreatedAt()));
                        }

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