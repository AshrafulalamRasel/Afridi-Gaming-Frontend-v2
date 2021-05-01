package com.itvillage.afridigaming.config;


import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.itvillage.afridigaming.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    private ArrayList<String> fileNameArray;
    private ArrayList<String> fileIdArray;
    private ArrayList<String> webUrlArray;
    private ArrayList<String> imageUrlArray;

    public ImageAdapter(Context context,ArrayList<String> fileNameArray, ArrayList<String> fileIdArray,
                        ArrayList<String> webUrlArray, ArrayList<String> imageUrlArray) {
        this.mContext = context;

        this.fileNameArray = fileNameArray;
        this.fileIdArray = fileIdArray;
        this.webUrlArray = webUrlArray;
        this.imageUrlArray = imageUrlArray;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(imageUrlArray.get(position)).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrlArray.get(position))));
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return imageUrlArray.size();
    }
}