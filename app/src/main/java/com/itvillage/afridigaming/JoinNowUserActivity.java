package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;
import com.itvillage.afridigaming.services.GetUserService;
import com.itvillage.afridigaming.services.RegistrationInGameService;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class JoinNowUserActivity extends AppCompatActivity {

    private String gameId, gameName, entryFeePerPerson, myBalance, gameType;
    private double totalEntryFee;
    private EditText playerId1EditText, playerId2EditText, playerId3EditText, playerId4EditText;
    private TextView myBalanceTextView, gameNameTextView, entryFeePerTotalMatchTextView, entryFeePerMatchTextView;
    private Button joinBut;
    private String squadPlayerNo = null;
    private RadioButton radioSolo,radioDuo,radioSquad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_now_user);

        gameId = getIntent().getExtras().getString("gameId");
        gameName = getIntent().getExtras().getString("gameName");
        totalEntryFee = Double.valueOf(getIntent().getExtras().getString("totalEntryFee"));
        entryFeePerPerson = getIntent().getExtras().getString("entryFeePerPerson");
        gameType = getIntent().getExtras().getString("gameType");

        myBalanceTextView = findViewById(R.id.myBalanceTextView);
        gameNameTextView = findViewById(R.id.gameNameTextView);
        entryFeePerTotalMatchTextView = findViewById(R.id.entryFeePerTotalMatchTextView);
        entryFeePerMatchTextView = findViewById(R.id.entryFeePerMatchTextView);

        playerId1EditText = findViewById(R.id.playerId1EditText);
        playerId2EditText = findViewById(R.id.playerId2EditText);
        playerId3EditText = findViewById(R.id.playerId3EditText);
        playerId4EditText = findViewById(R.id.playerId4EditText);

        radioSolo = findViewById(R.id.radioSolo);
        radioDuo = findViewById(R.id.radioDuo);
        radioSquad = findViewById(R.id.radioSquad);
        /*
        Radio Button Show By Game Type
        * */
        switch (gameType.toLowerCase()) {
            case "solo":
                radioSolo.setVisibility(View.VISIBLE);
                radioDuo.setVisibility(View.INVISIBLE);
                radioSquad.setVisibility(View.INVISIBLE);

                break;
            case "duo":
                radioSolo.setVisibility(View.VISIBLE);
                radioDuo.setVisibility(View.VISIBLE);
                radioSquad.setVisibility(View.INVISIBLE);

                break;
            case "squad":
                radioSolo.setVisibility(View.VISIBLE);
                radioDuo.setVisibility(View.VISIBLE);
                radioSquad.setVisibility(View.VISIBLE);

                break;
        }

        RadioGroup rg = (RadioGroup) findViewById(R.id.gameTypeGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                squadPlayerNo = ((RadioButton) findViewById(checkedId)).getText().toString();
                switch (squadPlayerNo.toLowerCase()) {
                    case "solo":
                        playerId1EditText.setEnabled(true);
                        playerId2EditText.setEnabled(false);
                        playerId3EditText.setEnabled(false);
                        playerId4EditText.setEnabled(false);
                        totalEntryFee =0.0;
                        totalEntryFee =Double.valueOf(entryFeePerPerson) *1;
                        entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                        break;
                    case "duo":
                        playerId1EditText.setEnabled(true);
                        playerId2EditText.setEnabled(true);
                        playerId3EditText.setEnabled(false);
                        playerId4EditText.setEnabled(false);
                        totalEntryFee =0.0;
                        totalEntryFee =Double.valueOf(entryFeePerPerson) *2;
                        entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                        break;
                    case "squad":
                        playerId1EditText.setEnabled(true);
                        playerId2EditText.setEnabled(true);
                        playerId3EditText.setEnabled(true);
                        playerId4EditText.setEnabled(true);
                        totalEntryFee =0.0;
                        totalEntryFee =Double.valueOf(entryFeePerPerson) *4;
                        entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                        break;
                }
                Toast.makeText(getBaseContext(), squadPlayerNo + " Selected", Toast.LENGTH_SHORT).show();
            }
        });


        getUserProfileBalance();

        joinBut = findViewById(R.id.joinBut);
        joinBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationInGame();
            }
        });

    }

    @SuppressLint("CheckResult")
    private void getUserProfileBalance() {
        GetUserService getUserService = new GetUserService(getApplicationContext());
        Observable<UserCreateProfileResponse> userCreateProfileResponseObservable =
                getUserService.getUserProfile();

        userCreateProfileResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserProfile -> {
                    myBalance = String.valueOf(getUserProfile.getAcBalance());
                    myBalanceTextView.setText("Available Balance : " + myBalance);
                    gameNameTextView.setText(gameName);
                    entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + entryFeePerPerson);
                    entryFeePerMatchTextView.setText("Game Entry Fee Per Person: " + entryFeePerPerson);
                }, err -> {

                });

    }

    @SuppressLint("CheckResult")
    private void registrationInGame() {

        RegistrationInGameService getUserService = new RegistrationInGameService(getApplicationContext());
        Observable<String> userCreateProfileResponseObservable =
                getUserService.registrationInGame(gameId, squadPlayerNo.toLowerCase(), playerId1EditText.getText().toString(), playerId2EditText.getText().toString(), playerId3EditText.getText().toString(),playerId4EditText.getText().toString());

        userCreateProfileResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginIn -> {

                    onLoginSuccess();

                }, throwable -> {
                    onLoginFailure(throwable);
                }, () -> {

                });
        //  Utility.onSuccessAlert("Join Successful",this);
        //  startActivity(new Intent(this.getApplicationContext(),UserHomeActivity.class));

    }

    private void onLoginFailure(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == 404) {
                Utility.onErrorAlert("Already Registered", this);

            } else if (httpException.code() == 500) {
                Utility.onErrorAlert("Inefficient Balance", this);
            } else {
                Utility.onErrorAlert("Something Wrong", this);
            }

        }
    }

    private void onLoginSuccess() {

        Utility.onSuccessAlert("Registration Success", this);
        Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_LONG).show();

    }
}