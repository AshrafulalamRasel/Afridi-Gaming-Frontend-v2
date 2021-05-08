package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.adapter.PaymentReportListAdapter;
import com.itvillage.afridigaming.dto.response.PaymentDetailsResponse;
import com.itvillage.afridigaming.services.GetPaymentReportListService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentReportActivity extends AppCompatActivity {

    private ListView report_lv;
    private ArrayList<String> usernameNameArray = new ArrayList<>();
    private ArrayList<String> emailArray = new ArrayList<>();
    private ArrayList<String> gameNameArray = new ArrayList<>();
    private ArrayList<String> winningStatusArray = new ArrayList<>();
    private ArrayList<Integer> perKillInGameArray = new ArrayList<>();
    private ArrayList<Integer> gamePerInvestArray = new ArrayList<>();
    private ArrayList<Double> incomeInPerGameArray = new ArrayList<>();
    private ArrayList<Integer> totalInvestment = new ArrayList<>();
    private ArrayList<Double> currentAccountArray = new ArrayList<>();
    private ArrayList<Double> refundArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getPaymentReportsList();
    }

    @SuppressLint("CheckResult")
    private void getPaymentReportsList() {
        GetPaymentReportListService getPaymentReportListService = new GetPaymentReportListService(this);
        Observable<List<PaymentDetailsResponse>> listObservable =
                getPaymentReportListService.getPaymentReportsList();


        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentDetailsResponses -> {
                    for (PaymentDetailsResponse paymentDetailsResponse : paymentDetailsResponses) {
                        usernameNameArray.add(paymentDetailsResponse.getUsername());
                        emailArray.add(paymentDetailsResponse.getEmail());
                        gameNameArray.add(paymentDetailsResponse.getGameName());
                        totalInvestment.add(paymentDetailsResponse.getGamePerInvest());
                        winningStatusArray.add(paymentDetailsResponse.getWinningStatus());
                        perKillInGameArray.add(paymentDetailsResponse.getPerKillInGame());
                        gamePerInvestArray.add(paymentDetailsResponse.getGamePerInvest());
                        incomeInPerGameArray.add(paymentDetailsResponse.getIncomeInPerGame());
                        currentAccountArray.add(paymentDetailsResponse.getCurrentAccount());
                        refundArray.add(paymentDetailsResponse.getReFound());
                        Log.e("v", paymentDetailsResponse.getEmail());
                    }
                    PaymentReportListAdapter adapter = new PaymentReportListAdapter(this, usernameNameArray,
                            emailArray, gameNameArray, winningStatusArray, perKillInGameArray,
                            incomeInPerGameArray, currentAccountArray, totalInvestment,refundArray);
                    report_lv = findViewById(R.id.report_lv);
                    report_lv.setAdapter(adapter);

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