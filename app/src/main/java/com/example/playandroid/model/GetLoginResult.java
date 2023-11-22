package com.example.playandroid.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.playandroid.entities.ProjectList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetLoginResult implements IGetLoginData{
    private Handler handler;
    String result;
    private void getLoginResultFirst(String userName,String password){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    getLoginResultString(httpUtil.postMethod("https://www.wanandroid.com/user/login",userName,password,"",2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getLoginResultString(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        String errorCode=jsonObject.getString("errorCode");
        if(errorCode.equals("0")){
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            String userName=jsonObject1.getString("nickname");
            Message message=new Message();
            message.obj=userName;
            handler.sendMessage(message);
        }else {
            String result=jsonObject.getString("errorMsg");
            Message message=new Message();
            message.obj=result;
            handler.sendMessage(message);
        }
    }
    @Override
    public void getLoginData(String userName, String password, SuccessLoginReturnData successLoginReturnData) {
        getLoginResultFirst(userName,password);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String result=(String)msg.obj;
                successLoginReturnData.Complete(result);
                super.handleMessage(msg);
            }
        };
    }
}
