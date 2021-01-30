package com.itvillage.afridigaming.ui.result;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.adapter.ResultListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.GetAllInActiveGamesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationsFragment extends Fragment {

    ListView list;
    private Button addGameBut;

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

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        setAllGamesInList();
        return root;
    }

    @SuppressLint("CheckResult")
    private void setAllGamesInList() {
        GetAllInActiveGamesService getAllInActiveGamesService = new GetAllInActiveGamesService(this.getActivity());
        Observable<List<GameResponse>> listObservable =
                getAllInActiveGamesService.getAllInActiveActiveGame();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameResponses -> {
                    for (GameResponse gameResponse : gameResponses) {

                        RegisterUsersInGameEntityArray.add(gameResponse.getRegisterUsersInGameEntities());

                        gameNameArray.add(gameResponse.getGameName() + " | "+ gameResponse.getGameplayOption()+" | " + gameResponse.getGameNumber());
                        gameSubNameArray.add(gameResponse.getGameplayStartTime());
                        imageArray.add(R.drawable.free_fire);
                        gameTotalPrizeArray.add(String.valueOf(gameResponse.getTotalPrize()));
                        gamePerKillPrizeArray.add(String.valueOf(gameResponse.getPerKillPrize()));
                        gameEntryFeeArray.add(String.valueOf(gameResponse.getEntryFee()));
                        gameTypeArray.add(gameResponse.getGameType());
                        gameVersionArray.add(gameResponse.getVersion());
                        gameMapArray.add(gameResponse.getMap());

                        winnerPrizeArray.add(String.valueOf(gameResponse.getWinnerPrize()));
                        secondPrizeArray.add(String.valueOf(gameResponse.getSecondPrize()));
                        thirdPrizeArray.add(String.valueOf(gameResponse.getThirdPrize()));

                        gameIsActiveList.add(gameResponse.isGameIsActive());


                    }
                    ResultListAdapter adapter = new ResultListAdapter(this.getActivity(), gameNameArray, gameSubNameArray,imageArray,RegisterUsersInGameEntityArray);
                    list = (ListView) this.getActivity().findViewById(R.id.resultList);
                    list.setAdapter(adapter);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}