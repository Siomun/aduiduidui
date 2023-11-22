package com.example.playandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.playandroid.R;
import com.example.playandroid.entities.KnowledgeHierarchyList;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRecyclerView=findViewById(R.id.history_recyclerview);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<String> historyDataList = (ArrayList) bundle.getSerializable("HistoryData");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        HistoryRecyclerAdapter historyRecyclerAdapter=new HistoryRecyclerAdapter(historyDataList);
        mRecyclerView.setAdapter(historyRecyclerAdapter);
    }
}