package com.example.playandroid.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.playandroid.entities.ProjectListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetProjectListDataImpl implements IGetDataIdPage{
    private ArrayList<ProjectListItem> mList=new ArrayList<>();
    private Handler handler;

    public void getProjectListDataFirst(String Url){
        new Thread(new Runnable() {//开启子线程
            @Override
            public void run() {
                HttpUtil httpUtil =new HttpUtil();
                try {
                    getProjectListDataString(httpUtil.httpUtil(Url));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void getProjectListDataString(String jsonData) throws JSONException {
        String title;
        String desc;
        String niceShareData;
        String author;
        String link;
        Bitmap bitmap;
        String bitmapPath;
        if(mList.isEmpty()){
            Log.d("sb","000000");
        }else {
            mList=new ArrayList<>();
        }
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject jsonObject1=jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("datas");
        for (int firstNumber = 0; firstNumber < jsonArray.length(); firstNumber++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(firstNumber);
            title=jsonObject2.getString("title");
            desc=jsonObject2.getString("desc");
            niceShareData=jsonObject2.getString("niceShareDate");
            author=jsonObject2.getString("author");
            link=jsonObject2.getString("link");
            bitmapPath=jsonObject2.getString("envelopePic");
            HttpURLConnection conn = null;
            try {
                //创建URL对象
                URL url = new URL(bitmapPath);
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
                    if(bitmap != null && !bitmap.isRecycled()){
                        ProjectListItem projectListItem=new ProjectListItem(title,desc,niceShareData,author,link,bitmap);
                        mList.add(projectListItem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭连接
            conn.disconnect();
        }
        Message message=new Message();
        message.obj=mList;
        handler.sendMessage(message);
    }


    @Override
    public void getData(String Id, int page, SuccessReturnDataPageId successReturnDataPageId) {
        String sPage=String.valueOf(page);
        String Url="https://www.wanandroid.com/project/list/"+sPage+"/json?cid="+Id;
        getProjectListDataFirst(Url);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                mList=(ArrayList<ProjectListItem>)msg.obj;
                successReturnDataPageId.Complete(mList);
                super.handleMessage(msg);
            }
        };
    }
}
