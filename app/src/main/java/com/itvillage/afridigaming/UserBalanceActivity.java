package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;
import com.itvillage.afridigaming.services.GetUserService;
import com.itvillage.afridigaming.services.PostMoneyRequestService;
import com.itvillage.afridigaming.services.WithdrawMoneyRequestService;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserBalanceActivity extends AppCompatActivity {

    TextView amount,amountD,winning_amount;
    Button add_money_help_but,withdraw_money_help_but;
    CardView bkash_but,rocket_but;
    String payAcType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_balance);

        amount = findViewById(R.id.amount);
        amountD = findViewById(R.id.amountD);
        withdraw_money_help_but = findViewById(R.id.withdraw_money_help_but);


        getUserProfile();

        add_money_help_but = findViewById(R.id.add_money_help_but);
        bkash_but = (CardView)findViewById(R.id.bkash_but);
        rocket_but = (CardView)findViewById(R.id.rocket_but);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_add_to_add_balance, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();


        add_money_help_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        bkash_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyAddRequest("bkash");
            }
        });
        rocket_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyAddRequest("rocket");
            }
        });
        withdraw_money_help_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_form_withdraw_money_details, viewGroup, false);
                builder.setView(dialogView);

                EditText amount_no_withdraw = dialogView.findViewById(R.id.amount_no_withdraw);
                EditText amount_withdraw = dialogView.findViewById(R.id.amount_withdraw);
                Button withdraw_but = dialogView.findViewById(R.id.withdraw_but);
                RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.paymentTypeGroup);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        payAcType = ((RadioButton)dialogView.findViewById(checkedId)).getText().toString();
                        Toast.makeText(getBaseContext(),  payAcType+" Selected", Toast.LENGTH_SHORT).show();
                    }
                });
                withdraw_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        withdrawMoney(payAcType,amount_withdraw.getText().toString(),amount_no_withdraw.getText().toString());
                        alertDialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    @SuppressLint("CheckResult")
    private void withdrawMoney(String transactionMethod, String loadBalAmount, String acNo) {

        WithdrawMoneyRequestService withdrawMoneyRequestService = new WithdrawMoneyRequestService(this);

        Observable<String> responseObservable = withdrawMoneyRequestService.withdrawMoneyRequestApi(transactionMethod,loadBalAmount,acNo);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    Utility.onSuccessAlert("Withdraw Request Send",this);

                }, throwable -> {
                    Utility.onErrorAlert("Withdraw Request Failed",this);
                }, () -> {

                });

    }
    private void moneyAddRequest(String by) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_from_for_money_request, viewGroup, false);
        EditText payment_amount = dialogView.findViewById(R.id.payment_amount);
        EditText payment_last_digit = dialogView.findViewById(R.id.payment_last_digit);
        EditText payment_method = dialogView.findViewById(R.id.payment_method);
        Button sent_request = dialogView.findViewById(R.id.sent_request);
        payment_method.setText(by);

        sent_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyRequest(by,payment_amount.getText().toString(),payment_last_digit.getText().toString());
                payment_amount.setText("");
                payment_last_digit.setText("");

            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("CheckResult")
    private void moneyRequest(String transactionMethod, String loadBalAmount, String loadBalAmlastThreeDigitount) {

        PostMoneyRequestService postMoneyRequestService = new PostMoneyRequestService(this);

        Observable<String> responseObservable = postMoneyRequestService.moneyRequest(transactionMethod,loadBalAmount,loadBalAmlastThreeDigitount);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    onLoginSuccess();

                }, throwable -> {
                    onLoginFailure(throwable);
                }, () -> {

                });

    }
    private void onLoginFailure(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == 500 || httpException.code() == 401) {
                Utility.onErrorAlert("Something Wrong",this);

            }
            Log.e("Error", "" + throwable.getMessage());
        }
    }

    private void onLoginSuccess() {

        Utility.onSuccessAlert("Payment Completed",this);

    }

    @SuppressLint("CheckResult")
    private void getUserProfile() {

        GetUserService getUserService = new GetUserService(this.getApplicationContext());
        Observable<UserCreateProfileResponse> userCreateProfileResponseObservable =
                getUserService.getUserProfile();

        userCreateProfileResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserProfile -> {

                    amount.setText(String.valueOf(getUserProfile.getAcBalance()));
                    amountD.setText("Deposited :"+String.valueOf(getUserProfile.getAcBalance()));
                    winning_amount.setText("Winning :"+String.valueOf(getUserProfile.getAcBalance()));


                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });

    }
}