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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.dto.response.CheckUpdateResponse;
import com.itvillage.afridigaming.services.CheckUpdateService;
import com.itvillage.afridigaming.services.NotificationBackgroundService;
import com.itvillage.afridigaming.util.ApplicationSharedPreferencesUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ME_ID = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationSharedPreferencesUtil applicationSharedPreferencesUtil = new ApplicationSharedPreferencesUtil(this);

        if (String.valueOf(getCurrentAppVersion()).equals(applicationSharedPreferencesUtil.getPref("version"))) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }, 2000);
        } else {
            checkUpdate();
        }

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
                    ApplicationSharedPreferencesUtil applicationSharedPreferencesUtil = new ApplicationSharedPreferencesUtil(this);

                    if (checkUpdateResponse.get(0).getVersionNo().equals(applicationSharedPreferencesUtil.getPref("version"))) {
                        Log.e(applicationSharedPreferencesUtil.getPref("version") + "----", checkUpdateResponse.get(0).getVersionNo());
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        }, 2000);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setCancelable(false);
                        builder.setTitle(checkUpdateResponse.get(0).getVersionNo() + " Update  is Available ");
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

}