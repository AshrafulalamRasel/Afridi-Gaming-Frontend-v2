package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.itvillage.afridigaming.adapter.GameListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.GetAllActiveGamesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GamesShowUserViewActivity extends AppCompatActivity {

    ArrayList<String> gameIdArray = new ArrayList<>();
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
    ArrayList<String> roomIdAndPassList = new ArrayList<>();

    ArrayList<List<RegisterUsersInGameEntity>> RegisterUsersInGameEntityArray = new ArrayList<>();

    private ListView game_list_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_show_user_view);

        setAllGamesInList();

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
                        gameNameArray.add(gameResponse.getGameName() + " | Mobile Only | " + gameResponse.getGameNumber());
                        gameSubNameArray.add(gameResponse.getCreatedAt());
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

                    }
                    GameListAdapter adapter = new GameListAdapter(this, gameIdArray,gameNameArray, gameSubNameArray,
                            imageArray, gameTotalPrizeArray, gamePerKillPrizeArray,
                            gameEntryFeeArray, gameTypeArray, gameVersionArray, gameMapArray,winnerPrizeArray,secondPrizeArray,thirdPrizeArray,RegisterUsersInGameEntityArray,roomIdAndPassList);
                    game_list_show = (ListView) findViewById(R.id.game_list_show);
                    game_list_show.setAdapter(adapter);

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}