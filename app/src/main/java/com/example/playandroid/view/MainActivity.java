package com.example.playandroid.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.playandroid.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager2 viewPager;
    private LinearLayout llHome,llKnowledgeHierarchy,llUser,llProject;
    private ImageView ivHome,ivKnowledgeHierarchy,ivUser,ivProject,ivCurrent;
    private IntentFilter intentFilter;
    private  NetworkChangeReceiver networkChangeReceiver;
    private Toolbar toolbar;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
        initPaper();
        initTabView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);//给后面开启的活动传值
                startActivity(intent);
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);//取消注册
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=(ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){

            }else {
                Toast.makeText(context,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }

        }}

    private void initTabView() {
        llHome=findViewById(R.id.home);
        llHome.setOnClickListener(this);
        llKnowledgeHierarchy=findViewById(R.id.knowledge_hierarchy);
        llKnowledgeHierarchy.setOnClickListener(this);
        llUser=findViewById(R.id.user);
        llUser.setOnClickListener(this);
        llProject=findViewById(R.id.project);
        llProject.setOnClickListener(this);
        ivHome=findViewById(R.id.home_photo);
        ivKnowledgeHierarchy=findViewById(R.id.knowledge_hierarchy_photo);
        ivUser=findViewById(R.id.user_photo);
        ivProject=findViewById(R.id.project_photo);
        ivHome.setSelected(true);
        ivCurrent=ivHome;
        toolbar=findViewById(R.id.main_too_bar);
    }

    private void initPaper() {
        viewPager=findViewById(R.id.view_paper_main);
        ActionBar actionBar=getSupportActionBar();
        ArrayList<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(KnowledgeHierarchyFragment.newInstance());
        fragmentList.add(ProjectFragment.newInstance());
        fragmentList.add(UserFragment.newInstance("用户"));
        MainFragmentAdapter mainFragmentAdapter=new MainFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        viewPager.setAdapter(mainFragmentAdapter);
        //禁止ViewPaper2的滑动
        viewPager.setUserInputEnabled(false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//滚动的动画
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {//页面选择了之后，实现响应事件
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position){
            case R.id.home:
                viewPager.setCurrentItem(0);
            case 0:
                ivHome.setSelected(true);
                ivCurrent=ivHome;
                break;
            case R.id.knowledge_hierarchy:
                viewPager.setCurrentItem(1);
            case 1:
                ivCurrent=ivKnowledgeHierarchy;
                ivKnowledgeHierarchy.setSelected(true);
                break;
            case R.id.project:
                viewPager.setCurrentItem(2);
            case 2:
                ivCurrent=ivProject;
                ivProject.setSelected(true);
                break;
            case R.id.user:
                viewPager.setCurrentItem(3);
            case 3:
                ivCurrent=ivUser;
                ivUser.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }
}