package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.itvillage.afridigaming.adapter.ApprovalListAdapter;
import com.itvillage.afridigaming.adapter.WithdrawApprovalListAdapter;
import com.itvillage.afridigaming.adapter.WithdrawSendApprovalListAdapter;
import com.itvillage.afridigaming.dto.response.RequestedNotificationResponse;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;
import com.itvillage.afridigaming.services.GetInactiveWithdrawListService;
import com.itvillage.afridigaming.services.GetSendWithdrawListService;
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
    private  String[] country = { "Withdraw Request", "Withdraw Send"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_withdraw_request_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Spinner action_type = (Spinner) findViewById(R.id.withdow_action_type);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        action_type.setAdapter(aa);

        action_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    getAllWithdrawRequestedNotification();
                }else{
                    sendAllWithdrawRequest();
                    Toast.makeText(getApplicationContext(), country[i], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @SuppressLint("CheckResult")
    private void sendAllWithdrawRequest() {
        idArray = new ArrayList<>();
        paymentGetawayNameArray = new ArrayList<>();
        amountArray = new ArrayList<>();
        lastThreeDigitOfPayableMobileNoArray = new ArrayList<>();
        currentBalanceArray = new ArrayList<>();
        updatedAtArray = new ArrayList<>();
        userNameArray = new ArrayList<>();

        GetSendWithdrawListService getInactiveWithdrawListService = new GetSendWithdrawListService(getApplicationContext());
        Observable<List<WithDrawMoneyResponse>> moneyRequestNotificationServiceOb = getInactiveWithdrawListService.getActiveWithdrawListApi();

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
                    WithdrawSendApprovalListAdapter adapter = new WithdrawSendApprovalListAdapter(this, idArray, paymentGetawayNameArray, amountArray, lastThreeDigitOfPayableMobileNoArray, currentBalanceArray, updatedAtArray,userNameArray);
                    list = (ListView) findViewById(R.id.approve_withdraw_list);
                    list.setAdapter(adapter);
                }, err -> {

                });
    }

    @SuppressLint("CheckResult")
    private void getAllWithdrawRequestedNotification() {
        idArray = new ArrayList<>();
        paymentGetawayNameArray = new ArrayList<>();
        amountArray = new ArrayList<>();
        lastThreeDigitOfPayableMobileNoArray = new ArrayList<>();
        currentBalanceArray = new ArrayList<>();
        updatedAtArray = new ArrayList<>();
        userNameArray = new ArrayList<>();

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