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

import com.example.playandroid.R;
import com.example.playandroid.entities.KnowledgeHierarchyListItem;
import com.example.playandroid.presenter.Presenter1;

import java.util.ArrayList;


public class KnowledgeHierarchyListFragment extends Fragment implements IView3{
    private ArrayList<KnowledgeHierarchyListItem> mKnowledgeHierarchyListItems;
    private ArrayList<KnowledgeHierarchyListItem> mTotalKnowledgeHierarchyListItems=new ArrayList<>();
    private View rootView;
    private RecyclerView mRecyclerView;
    private String Id;
    public Activity mActivity;
    private KnowledgeHierarchyListRecyclerViewAdapter knowledgeHierarchyListRecyclerViewAdapter;
    int page=0;
    Presenter1 presenter1;

    public KnowledgeHierarchyListFragment() {
    }

    public static KnowledgeHierarchyListFragment newInstance(String Id) {
        KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putString("Id",Id);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Id = getArguments().getString("Id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView =inflater.inflate(R.layout.fragment_knowledge_hierarchy_list, container, false);
        }
        mRecyclerView=(RecyclerView) rootView.findViewById(R.id.knowledge_hierarchy_listFragment_recyclerView);
        presenter1=new Presenter1(this);
        presenter1.fetchGetKnowledgeHierarchyList(Id,page);
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
        mKnowledgeHierarchyListItems=(ArrayList<KnowledgeHierarchyListItem>) list;
        mTotalKnowledgeHierarchyListItems.addAll(mKnowledgeHierarchyListItems);
        if(page==0){
            knowledgeHierarchyListRecyclerViewAdapter=new KnowledgeHierarchyListRecyclerViewAdapter(mKnowledgeHierarchyListItems);
            mRecyclerView.setAdapter(knowledgeHierarchyListRecyclerViewAdapter);
        }else {
           knowledgeHierarchyListRecyclerViewAdapter.updateData(mKnowledgeHierarchyListItems);
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
                        presenter1.fetchGetKnowledgeHierarchyList(Id,page);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        knowledgeHierarchyListRecyclerViewAdapter.setOnItemClickListener(new KnowledgeHierarchyListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title=mTotalKnowledgeHierarchyListItems.get(position).getTitle();
                String data=mTotalKnowledgeHierarchyListItems.get(position).getLink();
                Intent intent=new Intent(getActivity(), WebViewClick.class);//给后面开启的活动传值
                intent.putExtra("link",data);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });

    }
}