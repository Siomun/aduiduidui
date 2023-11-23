package com.example.playandroid.entities;

public class HomeTextItem {
    private String superChapterName;
    private String chapterName;
    private String niceDate;
    private String title;
    private String link;
    private String shareUser;

    public HomeTextItem(String superChapterName, String chapterName, String niceDate, String title, String link, String shareUser) {
        this.superChapterName = superChapterName;
        this.chapterName = chapterName;
        this.niceDate = niceDate;
        this.title = title;
        this.link = link;
        this.shareUser = shareUser;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

}
