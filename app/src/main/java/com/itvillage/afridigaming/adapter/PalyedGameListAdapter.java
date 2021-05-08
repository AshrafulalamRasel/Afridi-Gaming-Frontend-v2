package com.itvillage.afridigaming.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itvillage.afridigaming.R;

import java.util.ArrayList;

public class PalyedGameListAdapter extends ArrayAdapter<String> {

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
    private ArrayList<String> thirdPrizeArray ;

    public PalyedGameListAdapter(Activity context, ArrayList<String> gameIdArray, ArrayList<String> gameNameArray,
                           ArrayList<String> gameSubNameArray, ArrayList<Integer> imageArray,
                           ArrayList<String> gameTotalPrizeArray, ArrayList<String> gamePerKillPrizeArray,
                           ArrayList<String> gameEntryFeeArray, ArrayList<String> gameTypeArray,
                           ArrayList<String> gameVersionArray, ArrayList<String> gameMapArray,ArrayList<String> winnerPrizeArray,
                           ArrayList<String> secondPrizeArray,ArrayList<String> thirdPrizeArray) {
        super(context, R.layout.custom_pay_games_list_items, gameIdArray);

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

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_pay_games_list_items, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        TextView totalPrizeText = (TextView) rowView.findViewById(R.id.totalPrizeText);
        TextView totalKillText = (TextView) rowView.findViewById(R.id.totalKillText);
        TextView totalFeeText = (TextView) rowView.findViewById(R.id.totalFeeText);
        TextView typeText = (TextView) rowView.findViewById(R.id.typeText);
        TextView versionText = (TextView) rowView.findViewById(R.id.versionText);
        TextView mapText = (TextView) rowView.findViewById(R.id.mapText);

        Button prizeDetailsShowBut = rowView.findViewById(R.id.totalPrz);

        Button watch = rowView.findViewById(R.id.watch);


        titleText.setText(gameNameArray.get(position));
        imageView.setImageResource(imageArray.get(position));
        subtitleText.setText(gameSubNameArray.get(position));

        totalPrizeText.setText(gameTotalPrizeArray.get(position));
        totalKillText.setText(gamePerKillPrizeArray.get(position));
        totalFeeText.setText(gameEntryFeeArray.get(position));
        typeText.setText(gameTypeArray.get(position));
        versionText.setText(gameVersionArray.get(position));
        mapText.setText(gameMapArray.get(position));

        prizeDetailsShowBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                ViewGroup viewGroup = context.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_for_total_prz_show, viewGroup, false);

                TextView winnerPrize = (TextView) dialogView.findViewById(R.id.winnerPrize);
                TextView runnerUp1Prize = (TextView) dialogView.findViewById(R.id.runnerUp1Prize);
                TextView runnerUp2Prize = (TextView) dialogView.findViewById(R.id.runnerUp2Prize);

                winnerPrize.setText("Winner - "+ winnerPrizeArray.get(position) +" tk");
                runnerUp1Prize.setText("Runner Up 1 - "+ secondPrizeArray.get(position) +" tk");
                runnerUp2Prize.setText("Runner Up 2 - "+ thirdPrizeArray.get(position) +" tk");

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = context.getResources().getString(R.string.youtubelink);
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            }
        });
        return rowView;

    }

}