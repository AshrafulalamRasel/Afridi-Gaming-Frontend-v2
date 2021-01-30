package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.adapter.AdminGameListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.GetAllActiveGamesService;
import com.itvillage.afridigaming.services.GetAllGamesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GameListActivity extends AppCompatActivity {
    ListView list;
    private Button addGameBut;

    ArrayList<String> gameIdList = new ArrayList<>();
    ArrayList<String> roomIdAndPassList = new ArrayList<>();
    ArrayList<String> gameNameArray = new ArrayList<>();
    ArrayList<String> gameSubNameArray = new ArrayList<>();
    ArrayList<Integer> imageArray = new ArrayList<>();

    ArrayList<String> gameTotalPrizeArray = new ArrayList<>();
    ArrayList<String> gamePerKillPrizeArray = new ArrayList<>();
    ArrayList<String> gameEntryFeeArray = new ArrayList<>();
    ArrayList<String> gameTypeArray = new ArrayList<>();
    ArrayList<String> gameVersionArray = new ArrayList<>();
    ArrayList<String> gameMapArray = new ArrayList<>();

    ArrayList<String> winnerPrizeArray = new ArrayList<>();
    ArrayList<String> secondPrizeArray = new ArrayList<>();
    ArrayList<String> thirdPrizeArray = new ArrayList<>();

    ArrayList<Boolean> gameIsActiveList = new ArrayList<>();


    ArrayList<List<RegisterUsersInGameEntity>> RegisterUsersInGameEntityArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        addGameBut = findViewById(R.id.addGameBut);

        addGameBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewGameAdminActivity.class));
            }
        });

        setAllGamesInList();



    }

    @SuppressLint("CheckResult")
    private void setAllGamesInList() {
        GetAllGamesService getAllActiveGamesService = new GetAllGamesService(getApplicationContext());
        Observable<List<GameResponse>> listObservable =
                getAllActiveGamesService.getAllGames();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameResponses -> {
                    for (GameResponse gameResponse : gameResponses) {

                        RegisterUsersInGameEntityArray.add(gameResponse.getRegisterUsersInGameEntities());
                        gameIdList.add(gameResponse.getId());
                        gameNameArray.add(gameResponse.getGameName() + " | "+ gameResponse.getGameplayOption()+" | " + gameResponse.getGameNumber());
                        gameSubNameArray.add(gameResponse.getGameplayStartTime());
                        imageArray.add(R.drawable.free_fire);
                        gameTotalPrizeArray.add(String.valueOf(gameResponse.getTotalPrize()));
                        gamePerKillPrizeArray.add(String.valueOf(gameResponse.getPerKillPrize()));
                        gameEntryFeeArray.add(String.valueOf(gameResponse.getEntryFee()));
                        gameTypeArray.add(gameResponse.getGameType());
                        gameVersionArray.add(gameResponse.getVersion());
                        gameMapArray.add(gameResponse.getMap());
                        roomIdAndPassList.add("Room ID: "+gameResponse.getRoomId()+" | Password: "+ gameResponse.getRoomPassword()+"");

                        winnerPrizeArray.add(String.valueOf(gameResponse.getWinnerPrize()));
                        secondPrizeArray.add(String.valueOf(gameResponse.getSecondPrize()));
                        thirdPrizeArray.add(String.valueOf(gameResponse.getThirdPrize()));

                        gameIsActiveList.add(gameResponse.isGameIsActive());


                    }
                    AdminGameListAdapter adapter = new AdminGameListAdapter(this, gameIdList,gameNameArray, gameSubNameArray, imageArray,gameTotalPrizeArray,
                            gamePerKillPrizeArray, gameEntryFeeArray, gameTypeArray, gameVersionArray, gameMapArray,winnerPrizeArray,secondPrizeArray,thirdPrizeArray,
                            RegisterUsersInGameEntityArray,gameIsActiveList,roomIdAndPassList);
                    list = (ListView) findViewById(R.id.game_list);
                    list.setAdapter(adapter);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}