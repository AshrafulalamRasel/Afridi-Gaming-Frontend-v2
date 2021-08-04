package com.itvillage.afridigaming.ui.ongoing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.itvillage.afridigaming.GamesShowUserViewActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.config.ImageAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.dto.response.ImageUrlResponse;
import com.itvillage.afridigaming.services.GetAllActiveGamesService;
import com.itvillage.afridigaming.services.GetAllImagesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    TextView founded_match;
    private ArrayList<String> fileNameArray = new ArrayList<>();
    private ArrayList<String> fileIdArray = new ArrayList<>();
    private ArrayList<String> webUrlArray = new ArrayList<>();
    private ArrayList<String> imageUrlArray = new ArrayList<>();
    private ViewPager mViewPager;

    private int count = 0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        CardView free_fire_but = root.findViewById(R.id.free_fire_but);
        founded_match = root.findViewById(R.id.founded_match);
        mViewPager = (ViewPager) root.findViewById(R.id.viewPage);
        free_fire_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), GamesShowUserViewActivity.class));
            }
        });
        getAllIMages();
        setAllGamesInList();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CheckResult")
    private void getAllIMages() {


        GetAllImagesService getAllImagesService = new GetAllImagesService(this.getActivity());
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

                    ImageAdapter adapterView = new ImageAdapter(this.getActivity(), fileNameArray, fileIdArray, webUrlArray, imageUrlArray);
                    mViewPager.setAdapter(adapterView);

                    int max = imageUrlResponses.size();
                    Handler handler = new Handler();

                    final Runnable r = new Runnable() {
                        public void run() {
                            if (count <= max) {
                                mViewPager.setCurrentItem(count, true);
                                count++;
                            }else{
                                count=0;
                            }
                            handler.postDelayed(this, 3000);
                        }
                    };
                    handler.postDelayed(r, 2000);
                }, throwable -> {
                   Log.e("Error",throwable.getMessage());
                }, () -> {

                });
    }

    @SuppressLint("CheckResult")
    private void setAllGamesInList() {
        GetAllActiveGamesService getAllActiveGamesService = new GetAllActiveGamesService(this.getActivity());
        Observable<List<GameResponse>> listObservable =
                getAllActiveGamesService.getAllActiveGame();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameResponses -> {
                    founded_match.setText("" + gameResponses.size() + " Match Found");

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getRandomIndex(int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, max + 1);
        return max;
    }
}