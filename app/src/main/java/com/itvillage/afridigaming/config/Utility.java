package com.itvillage.afridigaming.config;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itvillage.afridigaming.R;

public class Utility {


    public static void wait(int time) {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                System.out.println("fdgdf");
                Log.e("Millisecond", "Pass");
            }
        }, time);
    }

    public static void onSuccessAlert(String mgs, Activity context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = context.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_success_dialog, viewGroup, false);
        TextView mgsText = dialogView.findViewById(R.id.mgs) ;
        mgsText.setText(mgs);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void onErrorAlert(String mgs, Activity context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = context.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_error_dialog, viewGroup, false);
        TextView mgsText = dialogView.findViewById(R.id.mgs) ;
        mgsText.setText(mgs);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
