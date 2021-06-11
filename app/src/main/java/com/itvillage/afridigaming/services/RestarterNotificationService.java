package com.itvillage.afridigaming.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


public class RestarterNotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context.getApplicationContext(), "Thanks For Afridi Gaming.",Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, NotificationBackgroundService.class));
        } else {
            context.startService(new Intent(context, NotificationBackgroundService.class));
        }
    }
}