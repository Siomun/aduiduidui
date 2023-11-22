package com.example.playandroid.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.playandroid.entities.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetSearchReturnResultImpl implements IGetDataIdPage{
    private ArrayList<SearchResult> list=new ArrayList<>();
    private Handler handler;
    public void getSearchReturnResultFirst(String Url,String param){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    getSearchReturnResultFirstString(httpUtil.postMethod(Url,param,"","",1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void getSearchReturnResultFirstString(String jsonData) throws JSONException {
        String superChapterName;
        String chapterName;
        String niceDate;
        String title;
        String author;
        String link;
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject jsonObject1=jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("datas");
        if(list.isEmpty()){
            Log.d("sb","000000");
        }else {
            list=new ArrayList<>();
        }
        for (int firstNumber = 0; firstNumber < jsonArray.length(); firstNumber++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(firstNumber);
            superChapterName=jsonObject2.getString("superChapterName");
            chapterName=jsonObject2.getString("chapterName");
            niceDate=jsonObject2.getString("niceDate");
            title=jsonObject2.getString("title");
            author=jsonObject2.getString("author");
            link=jsonObject2.getString("link");
            SearchResult searchResult=new SearchResult(superChapterName,chapterName,niceDate,title,author,link);
            list.add(searchResult);
        }
        Message message=new Message();
        message.obj=list;
        handler.sendMessage(message);
    }


    @Override
    public void getData(String param, int page, SuccessReturnDataPageId successReturnDataPageId) {
        String sPage=String.valueOf(page);
        String Url="https://www.wanandroid.com/article/query/"+sPage+"/json";
        Log.d("search",Url);
        getSearchReturnResultFirst(Url,param);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                list=(ArrayList<SearchResult>)msg.obj;
                successReturnDataPageId.Complete(list);
                super.handleMessage(msg);
            }
        };

    }
}
