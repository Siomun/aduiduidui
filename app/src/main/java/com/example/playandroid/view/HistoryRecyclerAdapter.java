package com.example.playandroid.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> mList;
    public HistoryRecyclerAdapter(List<String> list){
        mList=list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.history_recyclerview_textview);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_data_recyclerview,parent,false);
            return new HistoryRecyclerAdapter.ViewHolder(view);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            String data=mList.get(mList.size()-position-1);
            (((HistoryRecyclerAdapter.ViewHolder) holder).textView).setText(data);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            //最后一个 是底部item
            return 1;
        } else {
            return 0;
        }
    }
}
