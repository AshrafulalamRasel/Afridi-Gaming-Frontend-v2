package com.itvillage.afridigaming.ui.play;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.adapter.PalyedGameListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.GetAllInActiveGamesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DashboardFragment extends Fragment {
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

    private ListView game_list_show;


    List<RegisterUsersInGameEntity> RegisterUsersInGameEntityArray = new ArrayList<>();
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ListView playGameList = root.findViewById(R.id.playGameList);
//        PalyedGameListAdapter adapter = new PalyedGameListAdapter(this.getActivity() , maintitle, subtitle, imgid);
//        playGameList.setAdapter(adapter);
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

                        winnerPrizeArray.add(String.valueOf(gameResponse.getWinnerPrize()));
                        secondPrizeArray.add(String.valueOf(gameResponse.getSecondPrize()));
                        thirdPrizeArray.add(String.valueOf(gameResponse.getThirdPrize()));

                    }
                    PalyedGameListAdapter adapter = new PalyedGameListAdapter(this.getActivity(), gameIdArray, gameNameArray, gameSubNameArray,
                            imageArray, gameTotalPrizeArray, gamePerKillPrizeArray,
                            gameEntryFeeArray, gameTypeArray, gameVersionArray, gameMapArray, winnerPrizeArray, secondPrizeArray, thirdPrizeArray);
                    game_list_show = (ListView) this.getActivity().findViewById(R.id.playGameList);
                    game_list_show.setAdapter(adapter);

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}