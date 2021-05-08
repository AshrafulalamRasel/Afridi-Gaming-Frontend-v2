package com.itvillage.afridigaming.adapter;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itvillage.afridigaming.JoinNowUserActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;

import java.util.ArrayList;
import java.util.List;

public class PaymentReportListAdapter extends ArrayAdapter<String> {

    private Activity context;

    private ArrayList<String> usernameNameArray = new ArrayList<>();
    private ArrayList<String> emailArray = new ArrayList<>();
    private ArrayList<String> gameNameArray = new ArrayList<>();
    private ArrayList<String> winningStatusArray = new ArrayList<>();
    private ArrayList<Integer> perKillInGameArray = new ArrayList<>();
    private ArrayList<Integer> gamePerInvestArray = new ArrayList<>();
    private ArrayList<Double> incomeInPerGameArray = new ArrayList<>();
    private ArrayList<Double> currentAccountArray = new ArrayList<>();
    private ArrayList<Double> refundArray = new ArrayList<>();


    public PaymentReportListAdapter(Activity context, ArrayList<String> usernameNameArray, ArrayList<String> emailArray,
                                 ArrayList<String> gameNameArray, ArrayList<String> winningStatusArray,
                                 ArrayList<Integer> perKillInGameArray,
                                 ArrayList<Double> incomeInPerGameArray, ArrayList<Double> currentAccount,
                                    ArrayList<Integer> gamePerInvestArray, ArrayList<Double> refundArray) {
        super(context, R.layout.custom_payment_report_list_items, usernameNameArray);

        this.context = context;
        this.usernameNameArray = usernameNameArray;
        this.emailArray = emailArray;
        this.gameNameArray = gameNameArray;
        this.winningStatusArray = winningStatusArray;
        this.perKillInGameArray = perKillInGameArray;
        this.gamePerInvestArray = gamePerInvestArray;
        this.incomeInPerGameArray = incomeInPerGameArray;
        this.currentAccountArray = currentAccount;
        this.refundArray = refundArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_payment_report_list_items, null, true);
        TextView title_payment_report = rowView.findViewById(R.id.title_payment_report);
        TextView user_name_payment_report = rowView.findViewById(R.id.user_name_payment_report);
        TextView totalInvestText = rowView.findViewById(R.id.totalInvestText);
        TextView numberOfKillText = rowView.findViewById(R.id.numberOfKillText);
        TextView currentBalanceText = rowView.findViewById(R.id.currentBalanceText);
        TextView incomeText = rowView.findViewById(R.id.incomeText);
        TextView refundText = rowView.findViewById(R.id.refundext);
        Button gameStatusBut = rowView.findViewById(R.id.gameStatusBut);

        title_payment_report.setText(gameNameArray.get(position));
        user_name_payment_report.setText(usernameNameArray.get(position));
        totalInvestText.setText(String.valueOf(gamePerInvestArray.get(position)));
        numberOfKillText.setText(String.valueOf(perKillInGameArray.get(position)));
        currentBalanceText.setText(String.valueOf(currentAccountArray.get(position)));
        incomeText.setText(String.valueOf(incomeInPerGameArray.get(position)));
        gameStatusBut.setText(winningStatusArray.get(position));
        refundText.setText(String.valueOf(refundArray.get(position)));

        return rowView;

    }

}