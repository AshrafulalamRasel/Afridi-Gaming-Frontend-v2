package com.itvillage.afridigaming.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.itvillage.afridigaming.ImagesListActivity;
import com.itvillage.afridigaming.JoinNowUserActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.dto.response.GetProgessBarInfoResponse;
import com.itvillage.afridigaming.services.DeleteImageService;
import com.itvillage.afridigaming.services.GetProgressBarInfoService;
import com.itvillage.afridigaming.util.Utility;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.dto.response.RoomIdAndPasswordResponse;
import com.itvillage.afridigaming.services.GetRoomIdPasswordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GameListAdapter extends ArrayAdapter<String> {

    private Activity context;

    private ArrayList<String> gameIdArray;
    private ArrayList<String> gameNameArray;
    private ArrayList<String> gameSubNameArray;
    private ArrayList<Integer> imageArray;

    private ArrayList<String> gameTotalPrizeArray;
    private ArrayList<String> gamePerKillPrizeArray;
    private ArrayList<String> gameEntryFeeArray;
    private ArrayList<String> gameTypeArray;
    private ArrayList<String> gameVersionArray;
    private ArrayList<String> gameMapArray;

    private ArrayList<String> winnerPrizeArray;
    private ArrayList<String> secondPrizeArray;
    private ArrayList<String> thirdPrizeArray;
    private ArrayList<String> roomIdAndPassList;
    private ArrayList<String> maxPlayersList;

    private ArrayList<List<RegisterUsersInGameEntity>> registerUsersInGameEntityArray;
    private int progressStatus,progressMaxStatus;


    public GameListAdapter(Activity context, ArrayList<String> gameIdArray, ArrayList<String> gameNameArray,
                           ArrayList<String> gameSubNameArray, ArrayList<Integer> imageArray,
                           ArrayList<String> gameTotalPrizeArray, ArrayList<String> gamePerKillPrizeArray,
                           ArrayList<String> gameEntryFeeArray, ArrayList<String> gameTypeArray,
                           ArrayList<String> gameVersionArray, ArrayList<String> gameMapArray, ArrayList<String> winnerPrizeArray,
                           ArrayList<String> secondPrizeArray, ArrayList<String> thirdPrizeArray,
                           ArrayList<List<RegisterUsersInGameEntity>> registerUsersInGameEntityArray, ArrayList<String> roomIdAndPassList,
                           ArrayList<String> maxPlayersList) {
        super(context, R.layout.custom_game_list_items, gameIdArray);

        this.context = context;
        this.gameIdArray = gameIdArray;
        this.gameNameArray = gameNameArray;
        this.gameSubNameArray = gameSubNameArray;
        this.imageArray = imageArray;
        this.gameTotalPrizeArray = gameTotalPrizeArray;
        this.gamePerKillPrizeArray = gamePerKillPrizeArray;
        this.gameEntryFeeArray = gameEntryFeeArray;
        this.gameTypeArray = gameTypeArray;
        this.gameVersionArray = gameVersionArray;
        this.gameMapArray = gameMapArray;
        this.winnerPrizeArray = winnerPrizeArray;
        this.secondPrizeArray = secondPrizeArray;
        this.thirdPrizeArray = thirdPrizeArray;
        this.registerUsersInGameEntityArray = registerUsersInGameEntityArray;
        this.roomIdAndPassList = roomIdAndPassList;
        this.maxPlayersList = maxPlayersList;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_game_list_items, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        TextView totalPrizeText = (TextView) rowView.findViewById(R.id.totalPrizeText);
        TextView totalKillText = (TextView) rowView.findViewById(R.id.totalKillText);
        TextView totalFeeText = (TextView) rowView.findViewById(R.id.totalFeeText);
        TextView typeText = (TextView) rowView.findViewById(R.id.typeText);
        TextView versionText = (TextView) rowView.findViewById(R.id.versionText);
        TextView mapText = (TextView) rowView.findViewById(R.id.mapText);
        Button roomIdANdPass = (Button) rowView.findViewById(R.id.roomIdANdPassForUser);

        Button prizeDetailsShowBut = rowView.findViewById(R.id.prizeDetailsShowBut);
        Button joinNowBut = rowView.findViewById(R.id.joinNow);
        Button playersListBut = rowView.findViewById(R.id.playersListBut);

        CardView game_card = rowView.findViewById(R.id.game_card);
        TextView player_fill_up_showed = rowView.findViewById(R.id.player_fill_up_showed);
        ProgressBar progressBar = rowView.findViewById(R.id.progressBar);
        List<String> players = new ArrayList<>();
        for (RegisterUsersInGameEntity registerUsersInGameEntity : registerUsersInGameEntityArray.get(position)) {
            if (!registerUsersInGameEntity.getPartnerOneName().equals("")) {
                players.add(registerUsersInGameEntity.getPartnerOneName());
            }
            if (!registerUsersInGameEntity.getPartnerTwoName().equals("")) {
                players.add(registerUsersInGameEntity.getPartnerTwoName());
            }
            if (!registerUsersInGameEntity.getPartnerThreeName().equals("")) {
                players.add(registerUsersInGameEntity.getPartnerThreeName());
            }
            if (!registerUsersInGameEntity.getPartnerNameFour().equals("")) {
                players.add(registerUsersInGameEntity.getPartnerNameFour());
            }
        }
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(gameIdArray.get(position));
                GetProgressBarInfoService getRoomIdPasswordService = new GetProgressBarInfoService(context);
                Observable<GetProgessBarInfoResponse> responseObservable =
                        getRoomIdPasswordService.getProgessBarInfoByGameId(gameIdArray.get(position));

                responseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res -> {
                            progressStatus = Integer.valueOf(res.getTotalJoinPlayerInGame());
                            progressMaxStatus = Integer.valueOf(res.getTotalPlayerInGame());

                            player_fill_up_showed.setText(progressStatus + "/" + progressMaxStatus);
                            progressBar.setMax(progressMaxStatus);
                            progressBar.setProgress(progressStatus);
                        }, throwable -> {

                        }, () -> {

                        });

            }
        },1000,1000);




        titleText.setText(gameNameArray.get(position));
        imageView.setImageResource(imageArray.get(position));
        subtitleText.setText(gameSubNameArray.get(position));

        totalPrizeText.setText(gameTotalPrizeArray.get(position));
        totalKillText.setText(gamePerKillPrizeArray.get(position));
        totalFeeText.setText(gameEntryFeeArray.get(position));
        typeText.setText(gameTypeArray.get(position));
        versionText.setText(gameVersionArray.get(position));
        mapText.setText(gameMapArray.get(position));

        roomIdANdPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getGameIdAndPass(gameIdArray.get(position));

            }
        });

        prizeDetailsShowBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                ViewGroup viewGroup = context.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_for_total_prz_show, viewGroup, false);

                TextView winnerPrize = (TextView) dialogView.findViewById(R.id.winnerPrize);
                TextView runnerUp1Prize = (TextView) dialogView.findViewById(R.id.runnerUp1Prize);
                TextView runnerUp2Prize = (TextView) dialogView.findViewById(R.id.runnerUp2Prize);

                winnerPrize.setText("Winner - " + winnerPrizeArray.get(position) + " tk");
                runnerUp1Prize.setText("Runner Up 1 - " + secondPrizeArray.get(position) + " tk");
                runnerUp2Prize.setText("Runner Up 2 - " + thirdPrizeArray.get(position) + " tk");

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        joinNowBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetProgressBarInfoService getRoomIdPasswordService = new GetProgressBarInfoService(context);
                Observable<GetProgessBarInfoResponse> responseObservable =
                        getRoomIdPasswordService.getProgessBarInfoByGameId(gameIdArray.get(position));

                responseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res -> {
                            int progressStatus = Integer.valueOf(res.getTotalJoinPlayerInGame());
                            int progressMaxStatus = Integer.valueOf(res.getTotalPlayerInGame());
                            if (progressStatus < progressMaxStatus) {
                                if (progressStatus == progressMaxStatus) {
                                    Utility.onErrorAlert("Players Full", context);
                                } else {
                                    Intent intent = new Intent(context, JoinNowUserActivity.class);
                                    intent.putExtra("gameId", gameIdArray.get(position));
                                    intent.putExtra("gameName", gameNameArray.get(position));
                                    intent.putExtra("gameType", gameTypeArray.get(position));
                                    intent.putExtra("gameName", gameNameArray.get(position));
                                    intent.putExtra("totalEntryFee", String.valueOf(Integer.valueOf(gameEntryFeeArray.get(position)) * 3));
                                    intent.putExtra("entryFeePerPerson", gameEntryFeeArray.get(position));
                                    context.startActivity(intent);
                                }
                            } else {
                                Utility.onErrorAlert("Players Full", context);
                            }
                           // player_fill_up_showed.setText(progressStatus + "/" + progressMaxStatus);
                           // progressBar.setMax(progressMaxStatus);
                           // progressBar.setProgress(progressStatus);
                        }, throwable -> {

                        }, () -> {

                        });

            }
        });
        playersListBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                ViewGroup viewGroup = context.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_for_list_show, viewGroup, false);

                ArrayList<String> playerName = new ArrayList<>();
                if (registerUsersInGameEntityArray.isEmpty()) {
                    playerName.add("No Registered Player Found!!");
                }
                for (RegisterUsersInGameEntity registerUsersInGameEntity : registerUsersInGameEntityArray.get(position)) {
                    playerName.add(registerUsersInGameEntity.getPartnerOneName() + "," + registerUsersInGameEntity.getPartnerTwoName() + "," + registerUsersInGameEntity.getPartnerThreeName() + "," + registerUsersInGameEntity.getPartnerNameFour());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(dialogView.getContext(), android.R.layout.simple_list_item_1, playerName);
                ListView playerList = dialogView.findViewById(R.id.playerList);
                playerList.setAdapter(arrayAdapter);


                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = context.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_add_game_ins, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        game_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        return rowView;

    }


    @SuppressLint("CheckResult")
    private void getGameIdAndPass(String roomId) {
        GetRoomIdPasswordService getRoomIdPasswordService = new GetRoomIdPasswordService(context);
        Observable<RoomIdAndPasswordResponse> RegisterUsersInGameEntityArray =
                getRoomIdPasswordService.getRoomIdPassword(roomId);

        RegisterUsersInGameEntityArray.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    ViewGroup viewGroup = context.findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_room_id_password_show, viewGroup, false);

                    TextView room_id_and_pass = (TextView) dialogView.findViewById(R.id.room_id_and_pass);
                    room_id_and_pass.setText("Room Id:"+res.getRoomId()+" Password:"+res.getRoomPassword());

                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
    private boolean isPlayerRegister(List<RegisterUsersInGameEntity> registerUsersInGameEntities) {
        boolean res = false;
        for(RegisterUsersInGameEntity registerUsersInGameEntity: registerUsersInGameEntities)
        {

            if(registerUsersInGameEntity.getUserId().equals(Utility.loggedId)){
                res= true;
            }else {
                res= false;
            }
        }
        return res;
    }
}