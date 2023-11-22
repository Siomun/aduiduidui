package com.example.playandroid.model;

import android.os.Handler;
import android.os.Message;

import com.example.playandroid.entities.SearchHotWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetSearchHotWordImpl implements IGetData{
    ArrayList<SearchHotWord> mList=new ArrayList<>();
    private final static String Url="https://www.wanandroid.com//hotkey/json";
    private Handler handler;
    public void getSearchHotWordFirst(){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    getSearchHotWord(httpUtil.httpUtil(Url));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void getSearchHotWord(String jsonData) throws JSONException {
        String name;
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int number=0;number<jsonArray.length();number++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(number);
            name=jsonObject1.getString("name");
            SearchHotWord searchHotWord=new SearchHotWord(name);
            mList.add(searchHotWord);
        }
        Message message=new Message();
        message.obj=mList;
        handler.sendMessage(message);
    }


    @Override
    public void getData(SuccessReturnData successReturnData) {
        getSearchHotWordFirst();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                mList=(ArrayList<SearchHotWord>)msg.obj;
                successReturnData.Complete(mList);
                super.handleMessage(msg);
            }
        };
    }
}
