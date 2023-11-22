package com.example.playandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TableLayout;

import com.example.playandroid.R;
import com.example.playandroid.entities.KnowledgeHierarchyList;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeHierarchyListActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    ArrayList<Fragment> mFragmentList =new ArrayList<>();
    ArrayList<String> mName =new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_hierarchy_list);
        mTabLayout=findViewById(R.id.knowledge_hierarchy_tab_layout);
        mViewPager=findViewById(R.id.knowledge_hierarchy_view_paper);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<KnowledgeHierarchyList> knowledgeHierarchyLists = (ArrayList) bundle.getSerializable("DataList");
        for (int i = 0; i < knowledgeHierarchyLists.size(); i++) {
            Log.d("zwyt",knowledgeHierarchyLists.get(i).getId());
            mFragmentList.add(KnowledgeHierarchyListFragment.newInstance(knowledgeHierarchyLists.get(i).getId()));
            mName.add(knowledgeHierarchyLists.get(i).getName());
        }
        ProjectAdapter projectAdapter=new ProjectAdapter(getSupportFragmentManager(),mFragmentList,mName);
        mViewPager.setAdapter(projectAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换tab时不使用ViewPager动画效果
                mViewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}