package com.example.playandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.playandroid.R;
import com.example.playandroid.entities.ProjectListItem;
import com.example.playandroid.presenter.Presenter;
import com.example.playandroid.presenter.Presenter1;

import java.util.ArrayList;

public class ProjectListFragment extends Fragment implements IView3{

    private View rootView;
    private static String mProjectListId;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ProjectListRecyclerViewAdapter projectListRecyclerViewAdapter;
    public Activity mActivity;
    Presenter1 presenter;
    int page=0;
    private ArrayList<ProjectListItem> mProjectListItemArrayList;
    private ArrayList<ProjectListItem> mTotalProjectListItemArrayList=new ArrayList<>();
    public ProjectListFragment() {
        // Required empty public constructor
    }

    public static ProjectListFragment newInstance(String projectListId) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putString("projectListId",projectListId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectListId = getArguments().getString("projectListId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView =inflater.inflate(R.layout.fragment_project_list, container, false);
        }
        mProgressBar=rootView.findViewById(R.id.project_list_progressbar);
        mRecyclerView=rootView.findViewById(R.id.project_list_recycler_view);
        presenter=new Presenter1(this);
        presenter.fetchGetProjectListData(mProjectListId,page);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,DividerItemDecoration.VERTICAL));
        return rootView;
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.mActivity=(Activity)context;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void showData3(ArrayList<?> list) {
        mProjectListItemArrayList=(ArrayList<ProjectListItem>) list;
        mTotalProjectListItemArrayList.addAll(mProjectListItemArrayList);
       if(page==0){
            projectListRecyclerViewAdapter=new ProjectListRecyclerViewAdapter(mProjectListItemArrayList);
            mRecyclerView.setAdapter(projectListRecyclerViewAdapter);
        }else {
            projectListRecyclerViewAdapter.updateData(mProjectListItemArrayList);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if (lastItemPosition == (itemCount - 1) ) {
                        //加载更多
                        page++;
                        presenter.fetchGetProjectListData(mProjectListId,page);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        projectListRecyclerViewAdapter.setOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title=mTotalProjectListItemArrayList.get(position).getTitle();
                String data= mTotalProjectListItemArrayList.get(position).getLink();
                Intent intent=new Intent(getActivity(), WebViewClick.class);//给后面开启的活动传值
                intent.putExtra("title",title);
                intent.putExtra("link",data);
                startActivity(intent);
            }
        });
        mProgressBar.setVisibility(View.GONE);
    }

}