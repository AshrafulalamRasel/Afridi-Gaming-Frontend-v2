package com.itvillage.afridigaming;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.services.NotificationBackgroundService;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ME_ID=1337;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startService(new Intent(this, NotificationBackgroundService.class));
        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }, 2000);


    }


}