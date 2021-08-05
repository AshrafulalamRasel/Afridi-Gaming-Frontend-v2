package com.itvillage.afridigaming.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.itvillage.afridigaming.MainActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.dto.response.GetNotificationResponse;
import com.itvillage.afridigaming.util.ApplicationSharedPreferencesUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RestarterNotificationService extends BroadcastReceiver {
    private Timer timer;
    private TimerTask timerTask;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context.getApplicationContext(), "Notification Background Started",Toast.LENGTH_SHORT).show();


//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        Ringtone r = RingtoneManager.getRingtone(context, notification);
//        r.play();

        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                getNotificationDetails(context);

            }
        };
        timer.schedule(timerTask, 1000, 1000); //

    }



    private void getNotificationDetails(Context context) {
        GetNotificationListService getNotificationListService = new GetNotificationListService(context);
        Observable<List<GetNotificationResponse>> listObservable =
                getNotificationListService.getNotificationListService();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getNotificationResponse -> {
                    if (!getNotificationResponse.isEmpty()) {
                        ApplicationSharedPreferencesUtil applicationSharedPreferencesUtil = new ApplicationSharedPreferencesUtil(context);

                        if (getNotificationResponse.get(0).getId().equals(applicationSharedPreferencesUtil.getPref("previousId"))) {
                            //   Log.e(applicationSharedPreferencesUtil.getPref("previousId") + "----", getNotificationResponse.get(0).getId());

                        } else {
                            // Log.e(applicationSharedPreferencesUtil.getPref("previousId") + "###", getNotificationResponse.get(0).getId());
                            addNotification(getNotificationResponse.get(0).getNotificationSubject(),
                                    getNotificationResponse.get(0).getNotificationBody(),context);
                            applicationSharedPreferencesUtil.putPref("previousId", getNotificationResponse.get(0).getId());
                        }
                    } else {
                        //  Log.e("Notification Status: ", "No Notification Found");
                    }
                }, throwable -> {
                    //  Log.e("---------",throwable.getMessage());
                }, () -> {

                });
    }

    private void addNotification(String title, String body, Context context) {

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

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
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
}