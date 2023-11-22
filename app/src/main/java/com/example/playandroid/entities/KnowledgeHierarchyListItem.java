package com.example.playandroid.entities;

public class KnowledgeHierarchyListItem {
    /**chapterName": "Android Studio相关",
     "niceDate": "2020-04-14 00:05",
     "shareUser": "鸿洋",
     "superChapterName": "开发环境",
     "title": "数据库还可以这么看？（Android Studio 4.1 新特性）",
     * */
    private String chapterName;
    private String superChapterName;
    private String niceDate;
    private String title;
    private String shareUser;
    private String link;

    public KnowledgeHierarchyListItem(String chapterName,String superChapterName,String niceDate,String title,String shareUser,String link){
        this.chapterName=chapterName;
        this.superChapterName=superChapterName;
        this.niceDate=niceDate;
        this.shareUser=shareUser;
        this.title=title;
        this.link=link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
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

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }
}
