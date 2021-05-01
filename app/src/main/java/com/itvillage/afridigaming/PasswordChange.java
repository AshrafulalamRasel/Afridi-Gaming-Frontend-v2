package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.UpdatePasswordResponse;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;
import com.itvillage.afridigaming.services.UpdatePasswordService;
import com.itvillage.afridigaming.services.UserCreateService;
import com.itvillage.afridigaming.ui.me.MeFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PasswordChange extends AppCompatActivity {

    private TextInputEditText passwordEditText;
    private Button changeUpdatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passwordEditText = findViewById(R.id.passwordEditText);
        changeUpdatePassword = findViewById(R.id.changeUpdatePassword);


        changeUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword(passwordEditText.getText().toString());
                // startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }


    @SuppressLint("CheckResult")
    private void updatePassword(String password) {

        UpdatePasswordService updatePasswordService = new UpdatePasswordService(this);

        Observable<String> responseObservable = updatePasswordService.updatePassword(password);

        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createUser -> {
                    Utility.onSuccessAlert("Password Updated",this);
                }, throwable -> {
                    Utility.onErrorAlert("Something Wrong",this);
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