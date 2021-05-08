package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
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
    private String getGameType = null;
    // private RadioButton radioSolo,radioDuo,radioSquad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_now_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        switch (gameType.toLowerCase()) {
            case "solo":
                getGameType = "Solo";
                playerId1EditText.setVisibility(View.VISIBLE);
                playerId2EditText.setVisibility(View.INVISIBLE);
                playerId3EditText.setVisibility(View.INVISIBLE);
                playerId4EditText.setVisibility(View.INVISIBLE);
                totalEntryFee = 0.0;
                totalEntryFee = Double.valueOf(entryFeePerPerson) * 1;
                entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                break;
            case "duo":
                getGameType = "Duo";                playerId1EditText.setVisibility(View.VISIBLE);
                playerId2EditText.setVisibility(View.VISIBLE);
                playerId3EditText.setVisibility(View.INVISIBLE);
                playerId4EditText.setVisibility(View.INVISIBLE);
                totalEntryFee = 0.0;
                totalEntryFee = Double.valueOf(entryFeePerPerson) * 2;
                entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                break;
            case "squad":
                getGameType = "Squad";
                playerId1EditText.setVisibility(View.VISIBLE);
                playerId2EditText.setVisibility(View.VISIBLE);
                playerId3EditText.setVisibility(View.VISIBLE);
                playerId4EditText.setVisibility(View.VISIBLE);
                totalEntryFee = 0.0;
                totalEntryFee = Double.valueOf(entryFeePerPerson) * 4;
                entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                break;
            case "squad vs squad":
                getGameType = "Squad";
                playerId1EditText.setVisibility(View.VISIBLE);
                playerId2EditText.setVisibility(View.VISIBLE);
                playerId3EditText.setVisibility(View.VISIBLE);
                playerId4EditText.setVisibility(View.VISIBLE);
                totalEntryFee = 0.0;
                totalEntryFee = Double.valueOf(entryFeePerPerson) * 4;
                entryFeePerTotalMatchTextView.setText("Total Entry Fee : " + totalEntryFee);
                break;
        }
        getUserProfileBalance();

        joinBut = findViewById(R.id.joinBut);
        joinBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (gameType.toLowerCase()) {
                    case "solo":
                        if(playerId1EditText.getText().toString().equals("") )
                        {
                            Toast.makeText(getApplicationContext(),"Empty Filed Found",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            registrationInGame();
                        }
                        break;
                    case "duo":
                        if(playerId1EditText.getText().toString().equals("") ||
                                playerId2EditText.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Empty Filed Found",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            registrationInGame();
                        }
                        break;
                    case "squad":
                        if(playerId1EditText.getText().toString().equals("") ||
                                playerId2EditText.getText().toString().equals("")||
                                playerId3EditText.getText().toString().equals("") ||
                                playerId4EditText.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Empty Filed Found",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            registrationInGame();
                        }
                        break;
                    case "squad vs squad":
                        if(playerId1EditText.getText().toString().equals("") ||
                                playerId2EditText.getText().toString().equals("")||
                                playerId3EditText.getText().toString().equals("") ||
                                playerId4EditText.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Empty Filed Found",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            registrationInGame();
                        }
                        break;
                }

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
                getUserService.registrationInGame(gameId, getGameType.toLowerCase(), playerId1EditText.getText().toString(), playerId2EditText.getText().toString(), playerId3EditText.getText().toString(), playerId4EditText.getText().toString());

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