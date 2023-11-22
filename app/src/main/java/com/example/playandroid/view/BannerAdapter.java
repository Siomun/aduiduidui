package com.example.playandroid.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private final ArrayList<ImageView> mImageViewList;

    public BannerAdapter(ArrayList<ImageView> List){
        this.mImageViewList=List;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return  arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, @NonNull Object object) {
        view.removeView(mImageViewList.get(position));
    }

    @Override
    @NonNull
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }

}
