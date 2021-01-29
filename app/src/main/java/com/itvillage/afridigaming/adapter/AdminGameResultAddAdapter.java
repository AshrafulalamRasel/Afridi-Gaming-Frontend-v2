package com.itvillage.afridigaming.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.services.UpdateGameResultService;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdminGameResultAddAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String gameId;
    private ArrayList<String> userIdList;
    private ArrayList<String> userNameOfGame;
    private String squadPrize = null;

    public AdminGameResultAddAdapter(Activity context,
                                     ArrayList<String> userId, String gameId, ArrayList<String> userNameOfGame) {
        super(context, R.layout.custom_add_game_result_list_items, userId);

        this.context = context;
        this.userIdList = userId;
        this.gameId = gameId;
        this.userNameOfGame = userNameOfGame;

    }


    public View getView(int position, View view, ViewGroup parent) {
        final String[] numberOfKillIn = new String[1];
        ArrayList<String> numberOfKill = new ArrayList<>();
        for(int i=0;i<101;i++ )
        {
            Log.e("dsfds",String.valueOf(i));
            numberOfKill.add(String.valueOf(i));
        }

        if(userNameOfGame.isEmpty()) {
           Toast.makeText(context,"Player Not Found",Toast.LENGTH_SHORT).show();
           return null;
        }else {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.custom_add_game_result_list_items, null, true);

            TextView userNameOfGames = rowView.findViewById(R.id.userNameOfGames);


            Spinner spin = (Spinner) rowView.findViewById(R.id.spinner);
            ArrayAdapter aa = new ArrayAdapter(rowView.getContext(),android.R.layout.simple_spinner_item,numberOfKill);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(aa);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    numberOfKillIn[0] =numberOfKill.get(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //    EditText numberOfKill = rowView.findViewById(R.id.totalPrizeText);

//            numberOfKill.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("rfgdfg","rfdgfdg");
//                   // showKeyboard();
//                    InputMethodManager inputMethodManager = (InputMethodManager) rowView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                }
//            });

            Button save = rowView.findViewById(R.id.saveResult);

            RadioGroup rg = (RadioGroup) rowView.findViewById(R.id.gameTypeGroup);

            userNameOfGames.setText(userNameOfGame.get(position));

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    squadPrize = ((RadioButton) rowView.findViewById(checkedId)).getText().toString();

                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,numberOfKillIn[0] , Toast.LENGTH_LONG).show();
                   updateGameResult(gameId, userIdList.get(position), squadPrize, numberOfKillIn[0].toString());
                }
            });
            return rowView;
        }

    }

    @SuppressLint("CheckResult")
    private void updateGameResult(String gameId, String userId, String squadPrize, String numberOfKill) {

        UpdateGameResultService updateGameResultService = new UpdateGameResultService(context);

        Observable<String> responseObservable = updateGameResultService.updateGameResultService(gameId, userId, squadPrize, numberOfKill);


        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    onLoginSuccess();

                }, throwable -> {
                    onLoginFailure(throwable);
                }, () -> {

                });

    }


    private void onLoginFailure(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == 500 || httpException.code() == 401) {
                Utility.onErrorAlert("Something Wrong",context);

            }
            Log.e("Error", "" + throwable.getMessage());
        }
    }

    private void onLoginSuccess() {

        Utility.onSuccessAlert("Update Successful",context);

    }
}