package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.itvillage.afridigaming.adapter.GameListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.GetAllActiveGamesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GamesShowUserViewActivity extends AppCompatActivity {

    private ArrayList<String> gameIdArray = new ArrayList<>();
    private ArrayList<String> gameNameArray = new ArrayList<>();
    private ArrayList<String> gameSubNameArray = new ArrayList<>();
    private ArrayList<Integer> imageArray = new ArrayList<>();

    private ArrayList<String> gameTotalPrizeArray = new ArrayList<>();
    private ArrayList<String> gamePerKillPrizeArray = new ArrayList<>();
    private ArrayList<String> gameEntryFeeArray = new ArrayList<>();
    private ArrayList<String> gameTypeArray = new ArrayList<>();
    private ArrayList<String> gameVersionArray = new ArrayList<>();
    private ArrayList<String> gameMapArray = new ArrayList<>();

    private ArrayList<String> winnerPrizeArray = new ArrayList<>();
    private ArrayList<String> secondPrizeArray = new ArrayList<>();
    private ArrayList<String> thirdPrizeArray = new ArrayList<>();
    private ArrayList<String> roomIdAndPassList = new ArrayList<>();
    private ArrayList<String> maxPlayersList = new ArrayList<>();

    private ArrayList<List<RegisterUsersInGameEntity>> RegisterUsersInGameEntityArray = new ArrayList<>();

    private ListView game_list_show;
    private final String TAG = "GamesShowUserViewActivity.class";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_show_user_view);
        setAllGamesInList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @SuppressLint("CheckResult")
    private void setAllGamesInList() {
        GetAllActiveGamesService getAllActiveGamesService = new GetAllActiveGamesService(getApplicationContext());
        Observable<List<GameResponse>> listObservable =
                getAllActiveGamesService.getAllActiveGame();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameResponses -> {
                    for (GameResponse gameResponse : gameResponses) {

                        RegisterUsersInGameEntityArray.add(gameResponse.getRegisterUsersInGameEntities());
                        gameIdArray.add(gameResponse.getId());
                        gameNameArray.add(gameResponse.getGameName() + " | "+ gameResponse.getGameplayOption()+" | " + gameResponse.getGameNumber());
                        gameSubNameArray.add(gameResponse.getGameplayStartTime());
                        imageArray.add(R.drawable.free_fire);
                        gameTotalPrizeArray.add(String.valueOf(gameResponse.getTotalPrize()));
                        gamePerKillPrizeArray.add(String.valueOf(gameResponse.getPerKillPrize()));
                        gameEntryFeeArray.add(String.valueOf(gameResponse.getEntryFee()));
                        gameTypeArray.add(gameResponse.getGameType());
                        gameVersionArray.add(gameResponse.getVersion());
                        gameMapArray.add(gameResponse.getMap());
                        maxPlayersList.add(gameResponse.getMaxPlayers());
                        roomIdAndPassList.add("Room ID: " + gameResponse.getRoomId() + " | Password: " + gameResponse.getRoomPassword() + "");
                        winnerPrizeArray.add(String.valueOf(gameResponse.getWinnerPrize()));
                        secondPrizeArray.add(String.valueOf(gameResponse.getSecondPrize()));
                        thirdPrizeArray.add(String.valueOf(gameResponse.getThirdPrize()));

                    }
                    GameListAdapter adapter = new GameListAdapter(this, gameIdArray,gameNameArray, gameSubNameArray,
                            imageArray, gameTotalPrizeArray, gamePerKillPrizeArray,
                            gameEntryFeeArray, gameTypeArray, gameVersionArray, gameMapArray,winnerPrizeArray,secondPrizeArray,thirdPrizeArray,RegisterUsersInGameEntityArray,roomIdAndPassList,maxPlayersList);
                    game_list_show = (ListView) findViewById(R.id.game_list_show);
                    game_list_show.setAdapter(adapter);

                }, throwable -> {
                    throwable.printStackTrace();
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