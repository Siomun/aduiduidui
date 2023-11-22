package com.example.playandroid.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.playandroid.entities.KnowledgeHierarchyListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetKnowledgeHierarchyListImpl implements IGetDataIdPage{
    private ArrayList<KnowledgeHierarchyListItem> mList=new ArrayList<>();
    private Handler handler;
    private void getKnowledgeHierarchyListFirst(String Url){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    String s= httpUtil.httpUtil(Url);
                    getKnowledgeHierarchyListString(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getKnowledgeHierarchyListString(String jsonData) throws JSONException {
        String chapterName;
        String superChapterName;
        String niceDate;
        String title;
        String shareUser;
        String link;
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("datas");
        if(mList.isEmpty()){
            Log.d("sb","000000");
        }else {
            mList=new ArrayList<>();
        }
        for (int ArrayNumber = 0; ArrayNumber < jsonArray.length(); ArrayNumber++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(ArrayNumber);
            chapterName=jsonObject2.getString("chapterName");
            superChapterName=jsonObject2.getString("superChapterName");
            niceDate=jsonObject2.getString("niceDate");
            title=jsonObject2.getString("title");
            shareUser=jsonObject2.getString("shareUser");
            link=jsonObject2.getString("link");
            KnowledgeHierarchyListItem knowledgeHierarchyListItem=new KnowledgeHierarchyListItem(chapterName,superChapterName,niceDate,title,shareUser,link);
            mList.add(knowledgeHierarchyListItem);
        }
        Message message=new Message();
        message.obj=mList;
        handler.sendMessage(message);
    }

    @Override
    public void getData(String Id, int page, SuccessReturnDataPageId successReturnDataPageId) {
        String sPage=String.valueOf(page);
        String Url="https://www.wanandroid.com/article/list/"+sPage+"/json?cid="+Id;
        getKnowledgeHierarchyListFirst(Url);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                mList=(ArrayList<KnowledgeHierarchyListItem>)msg.obj;
                successReturnDataPageId.Complete(mList);
                super.handleMessage(msg);
            }
        };

    }
}
