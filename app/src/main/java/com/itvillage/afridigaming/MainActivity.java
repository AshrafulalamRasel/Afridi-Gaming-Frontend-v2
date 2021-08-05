package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.itvillage.afridigaming.dto.response.CheckUpdateResponse;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;
import com.itvillage.afridigaming.services.CheckUpdateService;
import com.itvillage.afridigaming.services.GetUserService;
import com.itvillage.afridigaming.services.LoginService;
import com.itvillage.afridigaming.services.NotificationBackgroundService;
import com.itvillage.afridigaming.util.ApplicationSharedPreferencesUtil;
import com.itvillage.afridigaming.util.Utility;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ME_ID = 1337;
    ApplicationSharedPreferencesUtil applicationSharedPreferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationSharedPreferencesUtil = new ApplicationSharedPreferencesUtil(this);

        checkUpdate();
//        if (String.valueOf(getCurrentAppVersion()).equals(applicationSharedPreferencesUtil.getPref("version"))) {
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                }
//            }, 2000);
//        } else {
//            checkUpdate();
//        }

       startService(new Intent(this, NotificationBackgroundService.class));


    }

    @SuppressLint("CheckResult")
    private void checkUpdate() {
        CheckUpdateService checkUpdateService = new CheckUpdateService(this);
        Observable<List<CheckUpdateResponse>> listObservable =
                checkUpdateService.checkUpdate();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(checkUpdateResponse -> {

                    if (checkUpdateResponse.get(0).getVersionNo().equals(String.valueOf(getCurrentAppVersion()))) {
                        Log.e(applicationSharedPreferencesUtil.getPref("version") + "----", checkUpdateResponse.get(0).getVersionNo());
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
//
//
//                            }
//                        }, 2000);
                        checkAutoLogin();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setCancelable(false);
                        builder.setTitle("V" + checkUpdateResponse.get(0).getVersionNo() + " Update  is Available ");
                        builder.setMessage("Are you want to update?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(checkUpdateResponse.get(0).getPlayStoreLink())));
                                applicationSharedPreferencesUtil.putPref("version", checkUpdateResponse.get(0).getVersionNo());
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        Log.e(applicationSharedPreferencesUtil.getPref("version") + "###", checkUpdateResponse.get(0).getVersionNo());


                    }


                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }

    private void checkAutoLogin() {
        if (applicationSharedPreferencesUtil.getPref("username") == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            login(applicationSharedPreferencesUtil.getPref("username"),
                    applicationSharedPreferencesUtil.getPref("password"));
        }
    }

    @SuppressLint("CheckResult")
    private void login(String username, String password) {

        LoginService loginService = new LoginService(this);

        Observable<LoginResponse> responseObservable = loginService.login(username, password);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginIn -> {
                    Utility.loggedId = String.valueOf(loginIn.getId());
                    Log.e("Access Token", String.valueOf(loginIn.getId()));
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

        Log.e("Access Token", String.valueOf(loginResponse.getAccessToken()));

        applicationSharedPreferencesUtil.saveAccessToken(String.valueOf(loginResponse.getAccessToken()));

        JWT parsedJWT = new JWT(String.valueOf(loginResponse.getAccessToken()));
        Claim subscriptionMetaData = parsedJWT.getClaim("scopes");
        String parsedValue = subscriptionMetaData.asString();


        Log.e("Access Token", parsedValue);

        if (parsedValue.equals("SUPER_ADMIN")) {
            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        } else if (parsedValue.equals("USER")) {
            userLoginSuccess();
        }

        Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_LONG).show();

    }

    public int getCurrentAppVersion() {
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
            return verCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;

        }
    }
    @SuppressLint("CheckResult")
    private void userLoginSuccess() {

        GetUserService getUserService = new GetUserService(this);
        Observable<UserCreateProfileResponse> userCreateProfileResponseObservable =
                getUserService.getUserProfile();

        userCreateProfileResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserProfile -> {

                    Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                    startActivity(intent);

                }, throwable -> {
                    startActivity(new Intent(getApplicationContext(), myProfileAdding.class));
                    throwable.printStackTrace();
                }, () -> {

                });

    }
}