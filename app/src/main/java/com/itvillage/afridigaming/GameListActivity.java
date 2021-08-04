package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.adapter.AdminGameListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.GetAllGamesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GameListActivity extends AppCompatActivity {
    private ListView list;
    private Button addGameBut,sreachButton;
    private EditText matchNoEditText;

    private List<GameResponse> gameResponseArrayList = new ArrayList<>();

    private ArrayList<String> gameIdList = new ArrayList<>();
    private ArrayList<String> roomIdAndPassList = new ArrayList<>();
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

    private ArrayList<Boolean> gameIsActiveList = new ArrayList<>();
    private ArrayList<String> maxPlayersList = new ArrayList<>();

    private ArrayList<List<RegisterUsersInGameEntity>> RegisterUsersInGameEntityArray = new ArrayList<>();
    private final String TAG = "RegisterUsersInGameEntity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        addGameBut = findViewById(R.id.addGameBut);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addGameBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewGameAdminActivity.class));
            }
        });

        sreachButton = findViewById(R.id.match_no_src_but);
        matchNoEditText = findViewById(R.id.match_no_src);
        sreachButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                sreachByMatchNo(matchNoEditText.getText().toString());
            }
        });

        setAllGamesInList();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sreachByMatchNo(String machId) {
        Log.d("Search By",machId );
        if(TextUtils.isEmpty(machId))
        {
            setAllGamesInList();
        }else {
            List<GameResponse> gameResponseList = gameResponseArrayList.stream().filter(result -> result.getGameNumber().equals(machId))
                    .collect(Collectors.toList());
            setDataInList(gameResponseList);
        }
    }

    @SuppressLint({"CheckResult", "LongLogTag"})
    private void setAllGamesInList() {
        GetAllGamesService getAllActiveGamesService = new GetAllGamesService(getApplicationContext());
        Observable<List<GameResponse>> listObservable =
                getAllActiveGamesService.getAllGames();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameResponses -> {
                    gameResponseArrayList = gameResponses;
                    setDataInList(gameResponses);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }

    private void setDataInList(List<GameResponse> gameResponses) {
        gameIdList = new ArrayList<>();
        roomIdAndPassList = new ArrayList<>();
        gameNameArray = new ArrayList<>();
        gameSubNameArray = new ArrayList<>();
        imageArray = new ArrayList<>();
        gameTotalPrizeArray = new ArrayList<>();
        gamePerKillPrizeArray = new ArrayList<>();
        gameEntryFeeArray = new ArrayList<>();
        gameTypeArray = new ArrayList<>();
        gameVersionArray = new ArrayList<>();
        gameMapArray = new ArrayList<>();
        winnerPrizeArray = new ArrayList<>();
        secondPrizeArray = new ArrayList<>();
        thirdPrizeArray = new ArrayList<>();
        gameIsActiveList = new ArrayList<>();
        maxPlayersList = new ArrayList<>();
        RegisterUsersInGameEntityArray = new ArrayList<>();

        for (GameResponse gameResponse : gameResponses) {

            RegisterUsersInGameEntityArray.add(gameResponse.getRegisterUsersInGameEntities());
            gameIdList.add(gameResponse.getId());
            gameNameArray.add(gameResponse.getGameName() + " | " + gameResponse.getGameplayOption() + " | " + gameResponse.getGameNumber());
            gameSubNameArray.add(gameResponse.getGameplayStartTime());
            imageArray.add(R.drawable.free_fire);
            gameTotalPrizeArray.add(String.valueOf(gameResponse.getTotalPrize()));
            gamePerKillPrizeArray.add(String.valueOf(gameResponse.getPerKillPrize()));
            gameEntryFeeArray.add(String.valueOf(gameResponse.getEntryFee()));
            gameTypeArray.add(gameResponse.getGameType());
            gameVersionArray.add(gameResponse.getVersion());
            gameMapArray.add(gameResponse.getMap());
            roomIdAndPassList.add("Room ID: " + gameResponse.getRoomId() + " | Password: " + gameResponse.getRoomPassword() + "");

            winnerPrizeArray.add(String.valueOf(gameResponse.getWinnerPrize()));
            secondPrizeArray.add(String.valueOf(gameResponse.getSecondPrize()));
            thirdPrizeArray.add(String.valueOf(gameResponse.getThirdPrize()));
            maxPlayersList.add(gameResponse.getMaxPlayers());
            gameIsActiveList.add(gameResponse.isGameIsActive());


        }
        AdminGameListAdapter adapter = new AdminGameListAdapter(this, gameIdList, gameNameArray, gameSubNameArray, imageArray, gameTotalPrizeArray,
                gamePerKillPrizeArray, gameEntryFeeArray, gameTypeArray, gameVersionArray, gameMapArray, winnerPrizeArray, secondPrizeArray, thirdPrizeArray,
                RegisterUsersInGameEntityArray, gameIsActiveList, roomIdAndPassList, maxPlayersList);
        list = (ListView) findViewById(R.id.game_list);
        list.setAdapter(adapter);
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