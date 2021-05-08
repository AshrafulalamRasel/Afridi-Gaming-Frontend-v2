package com.itvillage.afridigaming.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itvillage.afridigaming.ImagesListActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.util.Utility;
import com.itvillage.afridigaming.services.DeleteImageService;

import java.util.ArrayList;

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
                delete(fileIdArray.get(position));
                Toast.makeText(context, "Deleted" + fileIdArray.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;

    }

    @SuppressLint("CheckResult")
    private void delete(String id) {
        DeleteImageService deleteImageService = new DeleteImageService(context);
        Observable<String> listObservable =
                deleteImageService.deleteImages(id);

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageUrlResponses -> {
                    Utility.onSuccessAlert("Deleted", context);
                }, throwable -> {
                    Utility.onSuccessAlert("Deleted", context);
                    context.startActivity(new Intent(context, ImagesListActivity.class));

                }, () -> {

                });
    }


}