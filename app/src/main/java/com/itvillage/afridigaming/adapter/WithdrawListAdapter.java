package com.itvillage.afridigaming.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itvillage.afridigaming.R;

import java.util.ArrayList;

public class WithdrawListAdapter extends ArrayAdapter<String> {

    private Activity context;

    private ArrayList<String> paymentGetawayNameArray;
    private ArrayList<String> amountArray;
    private ArrayList<String> acNoOfPayableMobileNoArray;
    private ArrayList<String> userNameArray;
    private ArrayList<String> currentBalanceArray;
    private ArrayList<String> updatedAtArray;
    private ArrayList<Boolean> statusArray;



    public WithdrawListAdapter(Activity context,
                               ArrayList<String> paymentGetawayNameArray, ArrayList<String> amountArray,
                               ArrayList<String> acNoOfPayableMobileNoArray, ArrayList<String> userNameArray,
                               ArrayList<String> currentBalanceArray, ArrayList<String> updatedAtArray,ArrayList<Boolean> balanceStatusArray) {
        super(context, R.layout.custom_withdraw_history_list_items, paymentGetawayNameArray);

        this.context = context;
        this.paymentGetawayNameArray = paymentGetawayNameArray;
        this.amountArray = amountArray;
        this.acNoOfPayableMobileNoArray = acNoOfPayableMobileNoArray;
        this.userNameArray = userNameArray;
        this.currentBalanceArray = currentBalanceArray;
        this.updatedAtArray = updatedAtArray;
        this.statusArray = balanceStatusArray;

    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_withdraw_history_list_items, null, true);


        TextView payment_getway = (TextView) rowView.findViewById(R.id.payment_getway);
        TextView date_time = (TextView) rowView.findViewById(R.id.date_time);
        TextView tk = (TextView) rowView.findViewById(R.id.tk);
        TextView status_payment = (TextView) rowView.findViewById(R.id.status_payment);
        payment_getway.setText(paymentGetawayNameArray.get(position));
        date_time.setText(updatedAtArray.get(position));
        tk.setText(amountArray.get(position));
        if(statusArray.get(position)) {
            status_payment.setText("Request Status: Success");
        }else{
            status_payment.setText("Request Status: Pending");
        }
        return rowView;

    }

}