package com.itvillage.afridigaming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.itvillage.afridigaming.adapter.AdminGameListAdapter;
import com.itvillage.afridigaming.adapter.AdminImageListAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.ImageUrlResponse;
import com.itvillage.afridigaming.services.GetAllGamesService;
import com.itvillage.afridigaming.services.GetAllImagesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImagesListActivity extends AppCompatActivity {

    private ListView image_list;
    private ArrayList<String> fileNameArray= new ArrayList<>();
    private ArrayList<String> fileIdArray= new ArrayList<>();
    private ArrayList<String> webUrlArray= new ArrayList<>();
    private ArrayList<String> imageUrlArray= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list);

        getAllIMages();
    }

    @SuppressLint("CheckResult")
    private void getAllIMages() {
        GetAllImagesService getAllImagesService = new GetAllImagesService(getApplicationContext());
        Observable<List<ImageUrlResponse>> listObservable =
                getAllImagesService.getImages();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageUrlResponses -> {
                    for (ImageUrlResponse imageUrlResponse : imageUrlResponses) {
                        fileNameArray.add(imageUrlResponse.getFileName());
                        fileIdArray.add(imageUrlResponse.getFileId());
                        webUrlArray.add(imageUrlResponse.getWebUrl());
                        imageUrlArray.add(imageUrlResponse.getImageUrl());
                        Log.e("gfg",imageUrlResponse.getImageUrl());


                    }
                    AdminImageListAdapter adapter = new AdminImageListAdapter(this,fileNameArray,fileIdArray,webUrlArray,imageUrlArray);
                    image_list = findViewById(R.id.image_list);
                    image_list.setAdapter(adapter);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}