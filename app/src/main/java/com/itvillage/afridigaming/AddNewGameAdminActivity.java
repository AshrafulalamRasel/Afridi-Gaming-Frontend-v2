package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.itvillage.afridigaming.dto.request.GameSetRequest;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.services.CreateNewGameService;
import com.itvillage.afridigaming.services.LoginService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddNewGameAdminActivity extends AppCompatActivity {
    private TextInputEditText gameNumber,gameType,gameName,gameVersion,mapName,gameStatus,roomId,roomPass,totalPrize,winnerPrize,secPrize,trdPrize,perKillPrize,entryFee;
    private Button addNewGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_game_admin);

        gameNumber = findViewById(R.id.gameNumber);
        gameType = findViewById(R.id.gameType);
        gameName = findViewById(R.id.gameName);
        gameVersion = findViewById(R.id.gameVersion);
        mapName = findViewById(R.id.mapName);
        gameStatus = findViewById(R.id.gameStatus);
        roomId = findViewById(R.id.roomId);
        roomPass = findViewById(R.id.roomPass);
        totalPrize = findViewById(R.id.totalPrize);
        winnerPrize = findViewById(R.id.winnerPrize);
        secPrize = findViewById(R.id.secPrize);
        trdPrize = findViewById(R.id.trdPrize);
        perKillPrize = findViewById(R.id.perKillPrize);
        entryFee = findViewById(R.id.entryFee);

        addNewGame = findViewById(R.id.addNewGame);
        addNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame(gameNumber.getText().toString(), gameType.getText().toString(), gameName.getText().toString(),gameVersion.getText().toString(), mapName.getText().toString(),gameStatus.getText().toString(), roomId.getText().toString(),roomPass.getText().toString(),totalPrize.getText().toString()
              ,winnerPrize.getText().toString(),secPrize.getText().toString(),trdPrize.getText().toString(),perKillPrize.getText().toString(),entryFee.getText().toString());
            }
        });
    }


    @SuppressLint("CheckResult")
    private void addGame(String gameNumber,String gameType,String gameName , String version, String map,String gameStatus,
                         String roomId,String roomPassword,String totalPrize,String winnerPrize,String secondPrize,String thirdPrize,String perKillPrize,String entryFee) {

        CreateNewGameService createNewGameService = new CreateNewGameService(this);

        Observable<String> gameSetRequestObservable = createNewGameService.createGame(gameNumber,gameType,gameName,version,map,gameStatus,roomId,roomPassword,totalPrize,winnerPrize,secondPrize,thirdPrize,perKillPrize,entryFee);


        gameSetRequestObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createGameResponse -> {
                }, throwable -> {
                }, () -> {
                });
        startActivity(new Intent(getApplicationContext(),GameListActivity.class));

    }
}