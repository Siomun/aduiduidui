package com.example.playandroid.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {
    /**网络请求封装，“Get请求”
    * @return_description 返回值为网络请求后获得的输入流的字符串
    * */
    public String httpUtil(String Url){
        String jsonString="";
        HttpsURLConnection connection=null;
        BufferedReader reader=null;
        try {
            Log.d("zwyp",Url);
            URL url=new URL(Url);
            connection=(HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in=connection.getInputStream();//获取服务器返回的输入流
            reader=new BufferedReader(new InputStreamReader(in));//读取输入流
            StringBuilder response=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                response.append(line);
            }
            jsonString=response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                connection.disconnect();//把HTTP连接关掉
            }
        }
        return jsonString;
    }

    public String postMethod(String url,String param,String param1,String param2,int paramNumber){
        String key="";
        if(paramNumber==1){
            key="k="+param;
        }
        if(paramNumber==2){
            String userName="username="+param;
            String Password="password="+param1;
            key=userName+"&"+Password;
        }
        // 结果值
        StringBuffer rest=new StringBuffer();
        CookieManager manager = new CookieManager();
        //设置cookie策略，只接受与你对话服务器的cookie，而不接收Internet上其它服务器发送的cookie
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        HttpURLConnection conn=null;
        OutputStream out=null;
        BufferedReader br=null;
        try {
            // 创建 URL
            URL restUrl = new URL(url);
            // 打开连接
            conn= (HttpURLConnection) restUrl.openConnection();
            // 设置请求方式为 POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection","keep-Alive");
            // 设置接收文件类型
            conn.setRequestProperty("Accept","application/json");
            //设置发送文件类型
            /**
             这里注意  传递JSON数据的话 就要设置
             普通参数的话 就要注释掉
             */
            //conn.setRequestProperty("Content-Type","application/json");
            // 输入 输出 都打开
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //开始连接
            conn.connect();

            // 传递参数  流的方式
            out=conn.getOutputStream();
            out.write(key.getBytes());
            out.flush();

            // 读取数据
            br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line=null;
            while (null != (line=br.readLine())){
                rest.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭所有通道
            try {
                if (br!=null) {
                    br.close();
                }
                if (out!=null) {
                    out.close();
                }
                if (conn!=null) {
                    conn.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rest.toString();
    }

}
