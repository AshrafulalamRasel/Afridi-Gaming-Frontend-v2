package com.itvillage.afridigaming.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.itvillage.afridigaming.R;

import java.util.ArrayList;

public class GameResultListAdapter extends ArrayAdapter<String> {

    private Activity context;
    ArrayList<String> totalKillArray;
    ArrayList<String> totalEarnArray;
    ArrayList<String> partnerTypeArray ;
    ArrayList<String> partnerOneNameArray ;
    ArrayList<String> partnerTwoNameArray ;
    ArrayList<String> partnerThreeNameArray ;
    ArrayList<String> gameWinningStatusArray;

    public GameResultListAdapter(Activity context, ArrayList<String> totalKillArray,
            ArrayList<String> totalEarnArray,
            ArrayList<String> partnerTypeArray ,
            ArrayList<String> partnerOneNameArray ,
            ArrayList<String> partnerTwoNameArray ,
            ArrayList<String> partnerThreeNameArray, ArrayList<String> gameWinningStatusArray) {
        super(context, R.layout.custom_result_game_list_items,totalKillArray);

        this.context = context;

        this.totalKillArray = totalKillArray;
        this.totalEarnArray = totalEarnArray;
        this.partnerTypeArray = partnerTypeArray;
        this.partnerOneNameArray = partnerOneNameArray;
        this.partnerTwoNameArray = partnerTwoNameArray;
        this.partnerThreeNameArray = partnerThreeNameArray;
        this.gameWinningStatusArray = gameWinningStatusArray;


    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_result_game_list_items, null, true);

        TextView player_id = rowView.findViewById(R.id.player_id);
        TextView serial_no = rowView.findViewById(R.id.serial_no);
        TextView subtitle = rowView.findViewById(R.id.sub_title);
        TextView total_kill = rowView.findViewById(R.id.total_kille);
        TextView total_earn = rowView.findViewById(R.id.total_earne);
        Button status = rowView.findViewById(R.id.status);

        serial_no.setText(String.valueOf(position + 1));
        player_id.setText(partnerOneNameArray.get(position));
        subtitle.setText(String.valueOf(partnerTwoNameArray.get(position) + " " + partnerThreeNameArray.get(position)));
        total_kill.setText(String.valueOf(totalKillArray.get(position)));
        total_earn.setText(String.valueOf(totalEarnArray.get(position)));
        if(gameWinningStatusArray.get(position) == null)
        {
            status.setText("Winning Status Not Found");
        }else{
            status.setText(gameWinningStatusArray.get(position));
        }

        return rowView;

    }


}