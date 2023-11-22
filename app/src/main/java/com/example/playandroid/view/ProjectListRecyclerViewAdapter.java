package com.example.playandroid.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entities.ProjectListItem;

import java.util.ArrayList;
import java.util.List;

public class ProjectListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private final ArrayList<ProjectListItem> mList;
    private boolean arriveBottom=false;

    private HomeRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private HomeRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(HomeRecyclerViewAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(HomeRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public ProjectListRecyclerViewAdapter(ArrayList<ProjectListItem> list){
        this.mList=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.project_list_recycler_view_textview1);
            textView2=itemView.findViewById(R.id.project_list_recycler_view_textview2);
            textView3=itemView.findViewById(R.id.project_list_recycler_view_textview3);
            textView4= itemView.findViewById(R.id.project_list_recycler_view_textview4);
            imageView=itemView.findViewById(R.id.project_list_recycler_view_imageview);
        }
    }
    public class FooterHolder extends RecyclerView.ViewHolder {
        TextView footerText;
        ProgressBar progressBar;
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
            footerText= itemView.findViewById(R.id.footer_text);
            progressBar=itemView.findViewById(R.id.pb_main_download);
            if(arriveBottom){
                progressBar.setVisibility(View.GONE);
                footerText.setText("已经到底了喔~~");
            }else if(mList.size()<15){
                progressBar.setVisibility(View.GONE);
                footerText.setText("已经到底了喔~~");
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //你的item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_recycler_view,parent,false);
            return new ViewHolder(view);
        } else {
            //底部“加载更多”item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footertext, parent, false);
            return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProjectListRecyclerViewAdapter.ViewHolder){
            ProjectListItem projectListItem=mList.get(position);
            (((ViewHolder) holder).imageView).setImageBitmap(projectListItem.getBitmap());
            (((ViewHolder) holder).textView1).setText(projectListItem.getTitle());
            (((ViewHolder) holder).textView2).setText(projectListItem.getDesc());
            (((ViewHolder) holder).textView3).setText("作者："+projectListItem.getAuthor());
            (((ViewHolder) holder).textView4).setText("时间："+projectListItem.getNiceShareData());
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
    public void updateData(List<ProjectListItem> list){
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
