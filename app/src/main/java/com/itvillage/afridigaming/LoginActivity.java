package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.google.android.material.textfield.TextInputEditText;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.services.LoginService;
import com.itvillage.afridigaming.util.ApplicationSharedPreferencesUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private ApplicationSharedPreferencesUtil perfUtil;
    private TextView contact_us,sign_up,forget_password;
    private Button sign_in_but;
    private TextInputEditText emailEditText,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contact_us = findViewById(R.id.contact_us);
        sign_up = findViewById(R.id.sign_up);
        forget_password = findViewById(R.id.forget_password);
        sign_in_but = findViewById(R.id.sign_in_but);

        emailEditText = findViewById(R.id.emailEditText);
        password = findViewById(R.id.password);
        sign_in_but.setText("Sign In");
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
        sign_in_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in_but.setText("Signing..");
              login(emailEditText.getText().toString(), password.getText().toString());


            }
        });
        perfUtil = new ApplicationSharedPreferencesUtil(getApplicationContext());
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = getResources().getString(R.string.telegramlink);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPasswordDialog();
            }
        });
    }

    private void forgetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_form_forget_password, viewGroup, false);

        EditText email = dialogView.findViewById(R.id.email_forget_password);
        EditText new_password_forget_password = dialogView.findViewById(R.id.new_password_forget_password);
        EditText retype_password_forget_password = dialogView.findViewById(R.id.retype_password_forget_password);
        if(retype_password_forget_password.getText().equals(new_password_forget_password.getText()))
        {
            resetPassword(email.getText().toString(),new_password_forget_password.getText().toString());
        }


        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @SuppressLint("CheckResult")
    private void resetPassword(String email, String new_password_forget_password) {

        LoginService loginService = new LoginService(this);

        Observable<String> responseObservable = loginService.resetPassword(email, new_password_forget_password);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginIn -> {

                    Utility.onSuccessAlert("Password Reset Successfully",this);

                }, throwable -> {
                    Utility.onErrorAlert("Invalid Email",this);
                }, () -> {

                });

    }

    @SuppressLint("CheckResult")
    private void login(String username, String password) {

        LoginService loginService = new LoginService(this);

        Observable<LoginResponse> responseObservable = loginService.login(username, password);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginIn -> {
                    Utility.loggedId =String.valueOf(loginIn.getId());
                    Log.e("Access Token",String.valueOf(loginIn.getId()));
                    onLoginSuccess(loginIn);

                }, throwable -> {
                    onLoginFailure(throwable);
                }, () -> {

                });

    }
    private void onLoginFailure(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == 500 || httpException.code() == 401) {
                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();

            }
            Log.e("Error", "" + throwable.getMessage());
        }
    }

    private void onLoginSuccess(LoginResponse loginResponse) {

        Log.e("Access Token",String.valueOf(loginResponse.getAccessToken()));

        perfUtil.saveAccessToken(String.valueOf(loginResponse.getAccessToken()));

        JWT parsedJWT = new JWT(String.valueOf(loginResponse.getAccessToken()));
        Claim subscriptionMetaData = parsedJWT.getClaim("scopes");
        String parsedValue = subscriptionMetaData.asString();


        Log.e("Access Token",parsedValue);

        if (parsedValue.equals("SUPER_ADMIN")){
            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        }

        else if (parsedValue.equals("USER")){
            Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
            startActivity(intent);
        }

        Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_LONG).show();

    }
}