package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.itvillage.afridigaming.dto.request.GameSetRequest;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.services.CreateNewGameService;
import com.itvillage.afridigaming.services.LoginService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddNewGameAdminActivity extends AppCompatActivity {
    private TextInputEditText gameNumber,gameName,gameVersion,mapName,
            gameMaxPlayer,roomId,roomPass,totalPrize,winnerPrize,secPrize,trdPrize,
            perKillPrize,entryFee,game_start_time,play_device;
    private Spinner gameType;

    private Button addNewGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_game_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gameNumber = findViewById(R.id.gameNumber);
        gameType =  (Spinner) findViewById(R.id.gameType);
        gameName = findViewById(R.id.gameName);
        gameVersion = findViewById(R.id.gameVersion);
        mapName = findViewById(R.id.mapName);
        gameMaxPlayer = findViewById(R.id.gameMaxPlayer);
        roomId = findViewById(R.id.roomId);
        roomPass = findViewById(R.id.roomPass);
        totalPrize = findViewById(R.id.totalPrize);
        winnerPrize = findViewById(R.id.winnerPrize);
        secPrize = findViewById(R.id.secPrize);
        trdPrize = findViewById(R.id.trdPrize);
        perKillPrize = findViewById(R.id.perKillPrize);
        entryFee = findViewById(R.id.entryFee);
        game_start_time = findViewById(R.id.game_start_time);
        play_device = findViewById(R.id.play_device);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("SOLO");
        categories.add("DUO");
        categories.add("SQUAD");
        categories.add("SQUAD VS SQUAD");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        gameType.setAdapter(dataAdapter);

        addNewGame = findViewById(R.id.addNewGame);
        addNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("---------",gameType.getSelectedItem().toString());
                addGame(gameNumber.getText().toString(), gameType.getSelectedItem().toString(), gameName.getText().toString(),gameVersion.getText().toString(), mapName.getText().toString(),gameMaxPlayer.getText().toString(), roomId.getText().toString(),roomPass.getText().toString(),totalPrize.getText().toString()
              ,winnerPrize.getText().toString(),secPrize.getText().toString(),trdPrize.getText().toString(),perKillPrize.getText().toString(),entryFee.getText().toString(),game_start_time.getText().toString(),play_device.getText().toString());
            }
        });
    }


    @SuppressLint("CheckResult")
    private void addGame(String gameNumber,String gameType,String gameName , String version, String map,String gameMaxPlayer,
                         String roomId,String roomPassword,String totalPrize,String winnerPrize,String secondPrize,String thirdPrize,String perKillPrize,String entryFee,String game_start_time,String play_device) {

        CreateNewGameService createNewGameService = new CreateNewGameService(this);

        Observable<String> gameSetRequestObservable = createNewGameService.createGame(gameNumber,gameType,gameName,version,map,gameMaxPlayer,roomId,roomPassword,totalPrize,winnerPrize,secondPrize,thirdPrize,perKillPrize,entryFee,game_start_time,play_device);


        gameSetRequestObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createGameResponse -> {
                }, throwable -> {
                }, () -> {
                });
        startActivity(new Intent(getApplicationContext(),GameListActivity.class));

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