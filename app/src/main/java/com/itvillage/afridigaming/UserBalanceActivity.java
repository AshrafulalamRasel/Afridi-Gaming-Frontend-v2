package com.itvillage.afridigaming;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;
import com.itvillage.afridigaming.services.GetUserService;
import com.itvillage.afridigaming.services.PostMoneyRequestService;
import com.itvillage.afridigaming.services.WithdrawMoneyRequestService;
import com.itvillage.afridigaming.util.Utility;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserBalanceActivity extends AppCompatActivity {

    private TextView amount, amountD, winning_amount;
    private Button add_money_help_but, withdraw_money_help_but;
    private CardView bkash_but, rocket_but, paytm_but;
    private String payAcType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_balance);

        amount = findViewById(R.id.amount);
        amountD = findViewById(R.id.amountD);
        winning_amount = findViewById(R.id.winning_amount);
        withdraw_money_help_but = findViewById(R.id.withdraw_money_help_but);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getUserProfile();

        add_money_help_but = findViewById(R.id.add_money_help_but);
        bkash_but = (CardView) findViewById(R.id.bkash_but);
        rocket_but = (CardView) findViewById(R.id.rocket_but);
        paytm_but = (CardView) findViewById(R.id.paytm_but);

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
        paytm_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyAddRequestByPayTM();
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
                        payAcType = ((RadioButton) dialogView.findViewById(checkedId)).getText().toString();
                        Toast.makeText(getBaseContext(), payAcType + " Selected", Toast.LENGTH_SHORT).show();
                    }
                });
                withdraw_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(amount_no_withdraw.getText())) {
                            amount_no_withdraw.setError("Required");
                        }
                        if (TextUtils.isEmpty(amount_withdraw.getText())) {
                            amount_withdraw.setError("Required");
                        }
                        if (TextUtils.isEmpty(payAcType)) {
                            Toast.makeText(getBaseContext(), "Withdraw Not Selected", Toast.LENGTH_SHORT).show();
                        } else {
                            withdrawMoney(payAcType, amount_withdraw.getText().toString(), amount_no_withdraw.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void moneyAddRequestByPayTM() {
        String[] options = {"PAYTM WALLET NUMBER", "PAYTM BANK NUMBER"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_from_for_paytm_request, viewGroup, false);
        EditText payment_amount = dialogView.findViewById(R.id.payment_amount);
        EditText payment_last_digit = dialogView.findViewById(R.id.payment_last_digit);
        Spinner payment_type = dialogView.findViewById(R.id.payment_type);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        payment_type.setAdapter(aa);
        Log.e("----", payment_type.getSelectedItem().toString());
        Button sent_request = dialogView.findViewById(R.id.sent_request);
        sent_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(payment_amount.getText())) {
                    payment_amount.setError("Required");
                }
                if (TextUtils.isEmpty(payment_last_digit.getText())) {
                    payment_last_digit.setError("Required");
                } else {
                    moneyRequest(payment_type.getSelectedItem().toString(), payment_amount.getText().toString(), payment_last_digit.getText().toString());
                    payment_amount.setText("");
                    payment_last_digit.setText("");
                }

            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @SuppressLint("CheckResult")
    private void withdrawMoney(String transactionMethod, String loadBalAmount, String acNo) {

        WithdrawMoneyRequestService withdrawMoneyRequestService = new WithdrawMoneyRequestService(this);

        Observable<String> responseObservable = withdrawMoneyRequestService.withdrawMoneyRequestApi(transactionMethod, loadBalAmount, acNo);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    Utility.onSuccessAlert("Withdraw Request Send", this);

                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;

                        if (httpException.code() == 500 || httpException.code() == 401) {
                            Utility.onErrorAlert("Only Winning Money Withdraw And Account Balance Must Be 100 Tk.", this);

                        }if(httpException.code() == 400)
                        {
                            Utility.onErrorAlert("Previous Withdraw pending. Please Wait. ", this);

                        }
                        Log.e("Error", "" + throwable.getMessage());
                    }

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
                if (TextUtils.isEmpty(payment_amount.getText())) {
                    payment_amount.setError("Required");
                }
                if (TextUtils.isEmpty(payment_last_digit.getText())) {
                    payment_last_digit.setError("Required");
                } else {
                    moneyRequest(by, payment_amount.getText().toString(), payment_last_digit.getText().toString());
                    payment_amount.setText("");
                    payment_last_digit.setText("");
                }
            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("CheckResult")
    private void moneyRequest(String transactionMethod, String loadBalAmount, String loadBalAmlastThreeDigitount) {

        PostMoneyRequestService postMoneyRequestService = new PostMoneyRequestService(this);

        Observable<String> responseObservable = postMoneyRequestService.moneyRequest(transactionMethod, loadBalAmount, loadBalAmlastThreeDigitount);


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
                Utility.onErrorAlert("Something Wrong", this);

            }
            Log.e("Error", "" + throwable.getMessage());
        }
    }

    private void onLoginSuccess() {

        Utility.onSuccessAlert("Payment Completed", this);

    }

    @SuppressLint("CheckResult")
    private void getUserProfile() {

        GetUserService getUserService = new GetUserService(this.getApplicationContext());
        Observable<UserCreateProfileResponse> userCreateProfileResponseObservable =
                getUserService.getUserProfile();

        userCreateProfileResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserProfile -> {
                    Double acBalance = getUserProfile.getAcBalance();
                    Double winingBalance = getUserProfile.getTotalEarn();
                    if (acBalance == null) {
                        acBalance = 0.0;
                    }
                    if (winingBalance == null) {
                        winingBalance = 0.0;
                    }
                    amountD.setText("Deposited :" + String.valueOf(acBalance));
                    winning_amount.setText("Winning :" + String.valueOf(winingBalance));
                    amount.setText(String.valueOf(acBalance+winingBalance));

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