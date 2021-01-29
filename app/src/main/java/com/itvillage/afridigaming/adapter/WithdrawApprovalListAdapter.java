package com.itvillage.afridigaming.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.itvillage.afridigaming.AdminHomeActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.UpdateNotificationStatusAndAddBalanceService;
import com.itvillage.afridigaming.services.UpdateWithdrawRequestService;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WithdrawApprovalListAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private ArrayList<String> idArray;
    private ArrayList<String> paymentGetawayNameArray;
    private ArrayList<String> amountArray;
    private ArrayList<String> lastThreeDigitOfPayableMobileNoArray;
    private ArrayList<String> currentBalanceArray;
    private ArrayList<String> updatedAtArray ;
    private ArrayList<String> userNameArray ;

    public WithdrawApprovalListAdapter(Activity context,
                                       ArrayList<String> idArray, ArrayList<String> paymentGetawayNameArray,
                                       ArrayList<String> amountArray, ArrayList<String> lastThreeDigitOfPayableMobileNoArray,
                                       ArrayList<String> currentBalanceArray, ArrayList<String> updatedAtArray, ArrayList<String> userNameArray) {
        super(context, R.layout.custom_withdraw_approval_list_items, idArray);

        this.context = context;
        this.idArray = idArray;
        this.paymentGetawayNameArray = paymentGetawayNameArray;
        this.amountArray = amountArray;
        this.lastThreeDigitOfPayableMobileNoArray = lastThreeDigitOfPayableMobileNoArray;
        this.currentBalanceArray = currentBalanceArray;
        this.updatedAtArray = updatedAtArray;
        this.userNameArray = userNameArray;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_withdraw_approval_list_items, null, true);

        TextView payment_way = (TextView) rowView.findViewById(R.id.payment_way);
        TextView amt_text = (TextView) rowView.findViewById(R.id.amt_text);
        TextView last_three_digit = (TextView) rowView.findViewById(R.id.last_three_digit);
        TextView user_name = (TextView) rowView.findViewById(R.id.user_name);

        payment_way.setText(paymentGetawayNameArray.get(position));
        amt_text.setText(amountArray.get(position));
        last_three_digit.setText(lastThreeDigitOfPayableMobileNoArray.get(position));
        user_name.setText("Requested By:" +userNameArray.get(position));

        Button deny = rowView.findViewById(R.id.deny);
        Button approve = rowView.findViewById(R.id.approve);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotificationStatusAndAddBalanceService(idArray.get(position));
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rowView;

    }

    @SuppressLint("CheckResult")
    private void updateNotificationStatusAndAddBalanceService(String id) {

        UpdateWithdrawRequestService updateWithdrawRequestService = new UpdateWithdrawRequestService(context);
        Observable<String> responseObservable = updateWithdrawRequestService.updateWithdrawRequestService(id);

        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createUser -> {
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

        Utility.onSuccessAlert("Approved",context);
        context.startActivity(new Intent(context,AdminHomeActivity.class));

    }
}