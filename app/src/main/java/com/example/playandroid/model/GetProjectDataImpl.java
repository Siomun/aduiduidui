package com.example.playandroid.model;

import android.os.Handler;
import android.os.Message;

import com.example.playandroid.entities.ProjectList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetProjectDataImpl implements IGetData{
    private ArrayList<ProjectList> mList=new ArrayList<>();
    private Handler handler;

    private void getProjectDataFirst(){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    getProjectDataString(httpUtil.httpUtil("https://www.wanandroid.com/project/tree/json"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getProjectDataString(String jsonData) throws JSONException {
        String name;
        String id;
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int ArrayNumber = 0; ArrayNumber < jsonArray.length(); ArrayNumber++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(ArrayNumber);
            name=jsonObject1.getString("name");
            id=jsonObject1.getString("id");
            ProjectList projectList=new ProjectList(name,id);
            mList.add(projectList);
        }
        Message message=new Message();
        message.obj=mList;
        handler.sendMessage(message);
    }

    @Override
    public void getData(SuccessReturnData successReturnData) {
        getProjectDataFirst();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                mList=(ArrayList<ProjectList>)msg.obj;
                successReturnData.Complete(mList);
                super.handleMessage(msg);
            }
        };
    }
}
