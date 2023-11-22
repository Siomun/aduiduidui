package com.example.playandroid.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.example.playandroid.entities.BannerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetBannerImpl implements IGetData{
    private final static String Url="https://www.wanandroid.com/banner/json";
    private ArrayList<BannerItem> list=new ArrayList<>();
    private Handler handler;

    public void getBannerDataFirst(){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    getBanner(httpUtil.httpUtil(Url));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void getBanner(String jsonData) throws JSONException {
        String title;
        String imagePath;
        String Url;
        Bitmap bitmap;
        JSONObject jsonObject1 = new JSONObject(jsonData);
        JSONArray jsonArray=jsonObject1.getJSONArray("data");
        for(int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);//一个大括号item
            title = jsonObject.getString("title");
            imagePath = jsonObject.getString("imagePath");
            Url=jsonObject.getString("url");
            HttpURLConnection conn = null;
            try {
                //创建URL对象
                URL url = new URL(imagePath);
                // 根据url 发送 http的请求
                conn = (HttpURLConnection) url.openConnection();
                // 设置请求的方式
                conn.setRequestMethod("GET");
                //设置超时时间
                conn.setConnectTimeout(5000);
                // 得到服务器返回的响应码
                int code = conn.getResponseCode();
                //请求网络成功后返回码是200
                if (code == 200) {
                    //获取输入流
                    InputStream is = conn.getInputStream();
                    //将流转换成Bitmap对象
                    bitmap = BitmapFactory.decodeStream(is);
                    //将更改主界面的消息发送给主线程
                    BannerItem bannerItem=new BannerItem(title,Url, bitmap);
                    list.add(bannerItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭连接
            conn.disconnect();
        }
        Message message=new Message();
        message.obj=list;
        handler.sendMessage(message);
    }
    @Override
    public void getData(SuccessReturnData successReturnData) {
        getBannerDataFirst();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                list=(ArrayList<BannerItem>)msg.obj;
                successReturnData.Complete(list);
                super.handleMessage(msg);
            }
        };
    }
}
