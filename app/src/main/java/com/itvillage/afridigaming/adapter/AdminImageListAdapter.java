package com.itvillage.afridigaming.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.config.Utility;
import com.itvillage.afridigaming.dto.response.RegisterUsersInGameEntity;
import com.itvillage.afridigaming.services.UpdateGameSatusService;
import com.itvillage.afridigaming.services.UpdateRoomDetailsService;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdminImageListAdapter extends ArrayAdapter<String> {

    private Activity context;

    private ArrayList<String> fileNameArray;
    private ArrayList<String> fileIdArray;
    private ArrayList<String> webUrlArray;
    private ArrayList<String> imageUrlArray;

    public AdminImageListAdapter(Activity context,
                                 ArrayList<String> fileNameArray, ArrayList<String> fileIdArray,
                                 ArrayList<String> webUrlArray, ArrayList<String> imageUrlArray) {
        super(context, R.layout.custom_admin_game_list_items, fileNameArray);

        this.context = context;
        this.fileNameArray = fileNameArray;
        this.fileIdArray = fileIdArray;
        this.webUrlArray = webUrlArray;
        this.imageUrlArray = imageUrlArray;


    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_image_list_items, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.imageName);
        TextView link = (TextView) rowView.findViewById(R.id.link);
      titleText.setText(fileNameArray.get(position));
        link.setText(webUrlArray.get(position));
        Button delete = rowView.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Toast.makeText(context,"Deleted"+ fileIdArray.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;

    }

}