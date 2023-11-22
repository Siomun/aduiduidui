package com.example.playandroid.entities;

import android.graphics.Bitmap;

public class ProjectListItem {
    private String title;
    private String desc;
    private String niceShareData;
    private String author;
    private String link;
    private Bitmap bitmap;
    public ProjectListItem(String title,String desc, String niceShareData, String author, String link, Bitmap bitmap){
        this.title=title;
        this.desc=desc;
        this.niceShareData=niceShareData;
        this.author=author;
        this.link=link;
        this.bitmap=bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNiceShareData() {
        return niceShareData;
    }

    public void setNiceShareData(String niceShareData) {
        this.niceShareData = niceShareData;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }



}
