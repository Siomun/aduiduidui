package com.example.playandroid.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entities.KnowledgeHierarchy;

import java.util.List;

public class KnowledgeHierarchyRecyclerViewAdapter extends RecyclerView.Adapter<KnowledgeHierarchyRecyclerViewAdapter.ViewHolder>{
    List<KnowledgeHierarchy> list;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    private HomeRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private HomeRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(HomeRecyclerViewAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(HomeRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public KnowledgeHierarchyRecyclerViewAdapter(List<KnowledgeHierarchy> list){
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1= itemView.findViewById(R.id.test_view1);
            textView2= itemView.findViewById(R.id.test_view2);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.knowledge_hierarchy_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            KnowledgeHierarchy knowledgeHierarchy=list.get(position);
            holder.textView1.setText(knowledgeHierarchy.getNameFirst());
            holder.textView2.setText(knowledgeHierarchy.getNameSecond());
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
