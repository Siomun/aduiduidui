package com.example.playandroid.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entities.ProjectList;
import com.example.playandroid.presenter.Presenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment implements IView,IView2{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private View rootView;
    private List<Fragment> mViewList;
    private List<String> mTitleList;
    ArrayList<ProjectList> mProjectLists =new ArrayList<>();
    ArrayList<Fragment> mFragmentList =new ArrayList<>();
    ArrayList<String> mProjectName =new ArrayList<>();
    Presenter presenter;

    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView =inflater.inflate(R.layout.fragment_project, container, false);
        }
        mTabLayout=rootView.findViewById(R.id.project_tab_layout);
        mViewPager=rootView.findViewById(R.id.project_view_paper);
        presenter=new Presenter(this,this);
        presenter.fetchGetProjectData();
        return rootView;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void showData(ArrayList<?> list) {
        mProjectLists =(ArrayList<ProjectList>)list;
        for (int i = 0; i < mProjectLists.size(); i++) {
            mProjectName.add(mProjectLists.get(i).getName());
            mFragmentList.add(ProjectListFragment.newInstance(mProjectLists.get(i).getId()));
        }
        ProjectAdapter projectAdapter=new ProjectAdapter(getChildFragmentManager(),mFragmentList,mProjectName);
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


    @Override
    public void showData2(ArrayList<?> list) {

    }
}