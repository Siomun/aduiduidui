package com.example.playandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.playandroid.R;

import java.io.FileOutputStream;
import java.io.IOException;

public class WebViewClick extends AppCompatActivity {
    //这个是点击跳转文章的WebView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment_click);
        Intent intent=getIntent();
        String link=intent.getStringExtra("link");
        String title=intent.getStringExtra("title");
        WebView webView=findViewById(R.id.home_fragment_click_web_view);
        ProgressBar progressBar=findViewById(R.id.Web_view_progressbar);
        //文件储存文章的标题和链接
        if(!title.isEmpty()){
            save(title);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try{
                    if(!url.startsWith("http://") && !url.startsWith("https://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;
                }
                //1：返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    //文件储存文章的标题和链接
    public void save(String title) {
        FileOutputStream fos = null;
        try {
            // Context.MODE_PRIVATE私有权限，Context.MODE_APPEND追加写入到已有内容的后面
            fos =openFileOutput("history", Context.MODE_APPEND);
            fos.write(title.getBytes());
            fos.write("\r\n".getBytes());//写入换行
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}