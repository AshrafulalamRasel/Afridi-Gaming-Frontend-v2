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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.itvillage.afridigaming.AdminHomeActivity;
import com.itvillage.afridigaming.LoginActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.UserHomeActivity;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.LoginResponse;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.dto.response.UpdatePasswordResponse;
import com.itvillage.afridigaming.services.UpdateNotificationStatusAndAddBalanceService;
import com.itvillage.afridigaming.services.UpdatePasswordService;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApprovalListAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private ArrayList<String> userIdList;
    private ArrayList<String> nameList;
    private ArrayList<String> paymentGetwayList;
    private ArrayList<String> amountList;
    private ArrayList<String> mobileLastDigit;
    private ArrayList<String> balanceIdList ;

    private List<RegisterUsersInGameEntity> registerUsersInGameEntityArray;
    public ApprovalListAdapter(Activity context,
                                ArrayList<String> userIdList, ArrayList<String> nameList,
                                ArrayList<String> paymentGetwayList, ArrayList<String> amountList,
                                ArrayList<String> mobileLastDigit,ArrayList<String> balanceIdList) {
        super(context, R.layout.custom_approval_list_items, userIdList);

        this.context = context;
        this.userIdList = userIdList;
        this.nameList = nameList;
        this.paymentGetwayList = paymentGetwayList;
        this.amountList = amountList;
        this.mobileLastDigit = mobileLastDigit;
        this.balanceIdList = balanceIdList;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_approval_list_items, null, true);

        TextView payment_way = (TextView) rowView.findViewById(R.id.payment_way);
        TextView amt_text = (TextView) rowView.findViewById(R.id.amt_text);
        TextView last_three_digit = (TextView) rowView.findViewById(R.id.last_three_digit);
        TextView user_name = (TextView) rowView.findViewById(R.id.user_name);

        payment_way.setText(paymentGetwayList.get(position));
        amt_text.setText(amountList.get(position));
        last_three_digit.setText(mobileLastDigit.get(position));
        user_name.setText("Requested By:" +nameList.get(position));

        Button deny = rowView.findViewById(R.id.deny);
        Button approve = rowView.findViewById(R.id.approve);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotificationStatusAndAddBalanceService(balanceIdList.get(position),userIdList.get(position),amt_text.getText().toString());
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
    private void updateNotificationStatusAndAddBalanceService(String balanceId,String userId,String balance) {

        UpdateNotificationStatusAndAddBalanceService updateNotificationStatusAndAddBalanceService = new UpdateNotificationStatusAndAddBalanceService(context);

        Observable<String> responseObservable = updateNotificationStatusAndAddBalanceService.updateNotificationStatusAndAddBalanceService(balanceId,userId,balance);

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