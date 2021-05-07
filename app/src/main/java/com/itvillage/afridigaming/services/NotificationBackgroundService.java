package com.itvillage.afridigaming.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.itvillage.afridigaming.MainActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.adapter.WithdrawListAdapter;
import com.itvillage.afridigaming.dto.response.GetNotificationResponse;
import com.itvillage.afridigaming.dto.response.WithDrawMoneyResponse;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationBackgroundService extends Service {
    public int counter=0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
//            startMyOwnForeground();
//        else
//            startForeground(1, new Notification());
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, MainActivity.class);
        this.sendBroadcast(broadcastIntent);
    }

    private void addNotification(String title, String body) {

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle(title);
        bigText.setSummaryText(body);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.logo_icon);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(body);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
    @SuppressLint("CheckResult")
    private void getNotificationDetails() {
        GetNotificationListService getNotificationListService = new GetNotificationListService(this);
        Observable<List<GetNotificationResponse>> listObservable =
                getNotificationListService.getNotificationListService();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getNotificationResponse -> {
                    if(!getNotificationResponse.isEmpty()) {
                        addNotification(getNotificationResponse.get(0).getNotificationSubject(),
                                getNotificationResponse.get(0).getNotificationBody());
                        Log.e("---------", getNotificationResponse.get(0).getNotificationSubject());
                    }else{
                        Log.e("---------", "No Notification Found");
                    }
                }, throwable -> {
                    Log.e("---------",throwable.getMessage());
                }, () -> {

                });
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
               // addNotification();
                Log.i("Count", "=========  "+ (counter++));
                getNotificationDetails();

            }
        };
        timer.schedule(timerTask, 20000, 1000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
