package com.itvillage.afridigaming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.services.NotificationService;
import com.itvillage.afridigaming.services.SignUpService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationActivity extends AppCompatActivity {
    private EditText notiSubjact, notiBody;
    private Button sendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notiSubjact = findViewById(R.id.notiSubjact);
        notiBody = findViewById(R.id.notiBody);
        sendNotification = findViewById(R.id.sendNotification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationService notificationService = new NotificationService(getApplicationContext());
                //  Observable<SignUpResponse> observable = notificationService.createPatientWithSignUP("fdg5645yt","fdgdf@gmai.com","123456ghjmj");
                Observable<String> observable = notificationService.postNotification(notiSubjact.getText().toString(),notiBody.getText().toString());
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(notification -> {
                            Toast.makeText(getApplicationContext(), "Notification Send.", Toast.LENGTH_LONG).show();

                        }, throwable -> {
                            Toast.makeText(getApplicationContext(), "Notification Send.", Toast.LENGTH_LONG).show();
                            Log.e("err", throwable.getMessage());
                            throwable.printStackTrace();
                        }, () -> {

                        });
            }
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