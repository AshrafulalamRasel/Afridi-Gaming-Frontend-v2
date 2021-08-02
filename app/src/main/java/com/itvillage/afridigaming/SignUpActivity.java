package com.itvillage.afridigaming;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.itvillage.afridigaming.dto.response.SignUpResponse;
import com.itvillage.afridigaming.services.SignUpService;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {

    private ImageView back;
    private TextInputEditText userNameEditText,emailEditText,passwordEditText;
    private Button sign_up_but;
    private AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = findViewById(R.id.back);
        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        sign_up_but = findViewById(R.id.signUpBut);

        // Validation
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{6,30}$";
        mAwesomeValidation.addValidation(this, R.id.userNameEditText, "[a-zA-Z0-9\\s]+", R.string.err_username);
        mAwesomeValidation.addValidation(this, R.id.emailEditText, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(this, R.id.passwordEditText, pattern, R.string.err_password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        sign_up_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAwesomeValidation.validate()) {
                    SignUpService createSignUpService = new SignUpService(getApplicationContext());
                    //  Observable<SignUpResponse> observable = createSignUpService.createPatientWithSignUP("fdg5645yt","fdgdf@gmai.com","123456ghjmj");
                    Observable<String> observable = createSignUpService.createPatientWithSignUP(userNameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString());
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(signUpPatient -> {

                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            }, throwable -> {
                                Toast.makeText(getApplicationContext(), "Username Already Taken!!", Toast.LENGTH_LONG).show();
                                Log.e("err", throwable.getMessage());
                                throwable.printStackTrace();
                            }, () -> {

                            });
                }
            }
        });
    }
}