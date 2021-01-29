package com.itvillage.afridigaming.ui.ongoing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.itvillage.afridigaming.GamesShowUserViewActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.config.ImageAdapter;
import com.itvillage.afridigaming.dto.response.GameResponse;
import com.itvillage.afridigaming.services.GetAllActiveGamesService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    TextView founded_match;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        CardView free_fire_but = root.findViewById(R.id.free_fire_but);
        founded_match = root.findViewById(R.id.founded_match);

        free_fire_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(root.getContext(), GamesShowUserViewActivity.class));
            }
        });
        setAllGamesInList();
        ViewPager mViewPager = (ViewPager) root.findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(root.getContext());
        mViewPager.setAdapter(adapterView);
        return root;
    }

    @SuppressLint("CheckResult")
    private void setAllGamesInList() {
        GetAllActiveGamesService getAllActiveGamesService = new GetAllActiveGamesService(this.getActivity());
        Observable<List<GameResponse>> listObservable =
                getAllActiveGamesService.getAllActiveGame();

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameResponses -> {
                    founded_match.setText(""+gameResponses.size() +" Match Found");

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}