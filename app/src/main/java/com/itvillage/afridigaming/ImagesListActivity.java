package com.itvillage.afridigaming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.itvillage.afridigaming.adapter.AdminImageListAdapter;
import com.itvillage.afridigaming.dto.response.ImageUrlResponse;
import com.itvillage.afridigaming.services.GetAllImagesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImagesListActivity extends AppCompatActivity {

    private ListView image_list;
    private ArrayList<String> fileNameArray = new ArrayList<>();
    private ArrayList<String> fileIdArray = new ArrayList<>();
    private ArrayList<String> webUrlArray = new ArrayList<>();
    private ArrayList<String> imageUrlArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


                    }
                    AdminImageListAdapter adapter = new AdminImageListAdapter(this, fileNameArray, fileIdArray, webUrlArray, imageUrlArray);
                    image_list = findViewById(R.id.image_list);
                    image_list.setAdapter(adapter);
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;

    }
}