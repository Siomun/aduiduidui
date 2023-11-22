package com.example.playandroid.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entities.KnowledgeHierarchyListItem;

import java.util.List;

public class KnowledgeHierarchyListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<KnowledgeHierarchyListItem> mList;
    private boolean arriveBottom=false;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    private KnowledgeHierarchyListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private KnowledgeHierarchyListRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(KnowledgeHierarchyListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(KnowledgeHierarchyListRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public KnowledgeHierarchyListRecyclerViewAdapter( List<KnowledgeHierarchyListItem> list){
        this.mList =list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.home_recycler_view_test_view1);
            textView2=itemView.findViewById(R.id.home_recycler_view_test_view2);
            textView3=itemView.findViewById(R.id.home_recycler_view_test_view3);
            textView4=itemView.findViewById(R.id.home_recycler_view_test_view4);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        TextView footerText;
        ProgressBar progressBar;
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
            footerText=itemView.findViewById(R.id.footer_text);
            progressBar=itemView.findViewById(R.id.pb_main_download);
            if(arriveBottom){
                progressBar.setVisibility(View.GONE);
                footerText.setText("已经到底了喔~~");
            }else if(mList.size()<20){
                progressBar.setVisibility(View.GONE);
                footerText.setText("已经到底了喔~~");
            }
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view,parent,false);
            return new ViewHolder(view);
        } else {
            //底部“加载更多”item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footertext, parent, false);
            return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof KnowledgeHierarchyListRecyclerViewAdapter.ViewHolder){
            KnowledgeHierarchyListItem knowledgeHierarchyListItem= mList.get(position);
            (((KnowledgeHierarchyListRecyclerViewAdapter.ViewHolder) holder).textView1).setText("类型："+knowledgeHierarchyListItem.getSuperChapterName()+"/"+knowledgeHierarchyListItem.getChapterName());
            (((KnowledgeHierarchyListRecyclerViewAdapter.ViewHolder) holder).textView2).setText("时间："+knowledgeHierarchyListItem.getNiceDate());
            (((KnowledgeHierarchyListRecyclerViewAdapter.ViewHolder) holder).textView3).setText(knowledgeHierarchyListItem.getTitle());
            (((KnowledgeHierarchyListRecyclerViewAdapter.ViewHolder) holder).textView4).setText("作者："+knowledgeHierarchyListItem.getShareUser());
            if(position!=0){
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

        }
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

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }
    //提供给外部调用的方法 刷新数据
    public void updateData(List<KnowledgeHierarchyListItem> list){
        if(list.isEmpty()){
            arriveBottom=true;
        }
        //再此处理获得的数据  list为传进来的数据
        //... list传进来的数据 添加到mList中
        for (int i = 0; i < list.size(); i++) {
            mList.add(list.get(i));
        }
        //通知适配器更新
        notifyDataSetChanged();
    }


}
