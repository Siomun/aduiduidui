package com.example.playandroid.entities;

import android.graphics.Bitmap;

public class BannerItem {


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    private Bitmap bitmap;
    private String title;
    private String Url;

    public BannerItem(String title, String Url, Bitmap bitmap){
        this.title=title;
        this.bitmap=bitmap;
        this.Url=Url;
    }


}
