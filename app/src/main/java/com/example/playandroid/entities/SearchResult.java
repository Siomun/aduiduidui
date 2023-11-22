package com.example.playandroid.entities;

public class SearchResult {
    /**
     *"superChapterName": "公众号",
     *"chapterName": "鸿洋",
     * "niceDate": "1天前",
     *"title": "两年经验妹子的<em class='highlight'>面试</em>总结",
     *"author": "鸿洋",
     * "link": "https://mp.weixin.qq.com/s/g-6XqdFxfPcx-Kyn7M_tvQ",
     */
    private String superChapterName;
    private String chapterName;
    private String niceDate;
    private String title;
    private String author;
    private String link;

    public SearchResult(String superChapterName,String chapterName,String niceDate,String title,String author,String link){
        this.superChapterName=superChapterName;
        this.chapterName=chapterName;
        this.niceDate=niceDate;
        this.title=title;
        this.author=author;
        this.link=link;
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

}
