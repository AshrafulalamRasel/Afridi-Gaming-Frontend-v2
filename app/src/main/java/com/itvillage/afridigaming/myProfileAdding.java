package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.itvillage.afridigaming.util.Utility;
import com.itvillage.afridigaming.services.UserCreateService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class myProfileAdding extends AppCompatActivity {

    private ImageView btnBack;
    private TextInputEditText firstNameEditText,lastNameEditText,mobileNumber;
    private Button crProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_adding);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // btnBack = findViewById(R.id.btnBackPares);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        mobileNumber = findViewById(R.id.mobileNumber);

        crProfile = findViewById(R.id.crProfile);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getApplicationContext(), MeFragment.class));
//            }
//        });

        crProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserProfileLog(firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),mobileNumber.getText().toString());
               // startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }


    @SuppressLint("CheckResult")
    private void createUserProfileLog(String fname, String lName,String mobile) {

        UserCreateService userCreateService = new UserCreateService(this);

        Observable<String> responseObservable = userCreateService.createUser(fname, lName,mobile);

        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createUser -> {

                    Utility.onSuccessAlert("Successfully Updated",this);

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