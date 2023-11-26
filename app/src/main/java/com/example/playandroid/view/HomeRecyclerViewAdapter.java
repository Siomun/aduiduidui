package com.example.playandroid.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.entities.BannerItem;
import com.example.playandroid.entities.HomeTextItem;
import com.example.playandroid.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<HomeTextItem> mList;
    //初始化ViewPager
    private View rootView;
    private int viewPaperClick;
    private ViewPager mViewPager;
    private TextView mTextView;
    private ImageView mImageView;
    private final ArrayList<ImageView> mImageViewList=new ArrayList<>();
    private int currentPosition;
    private final List<String> mBannerTitle=new ArrayList<>();
    private final List<String> mBannerLink=new ArrayList<>();
    private List<BannerItem> mBannerItemList=new ArrayList<>();
    private boolean beginCarousel=true;
    private boolean arriveBottom=false;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int count =5;
            int index=mViewPager.getCurrentItem();
            index=(index+1)%count;
            mViewPager.setCurrentItem(index);    //收到消息后设置viewPager当前要显示的图片
            mHandler.sendEmptyMessageDelayed(0, 1000*2);    //第一个参数随便写；第二个参数表示每两秒刷新一次
        }
    };
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public HomeRecyclerViewAdapter ( List<HomeTextItem> list){
        this.mList =list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1= itemView.findViewById(R.id.home_recycler_view_test_view1);
            textView2= itemView.findViewById(R.id.home_recycler_view_test_view2);
            textView3= itemView.findViewById(R.id.home_recycler_view_test_view3);
            textView4= itemView.findViewById(R.id.home_recycler_view_test_view4);
        }
    }


    public class FooterHolder extends RecyclerView.ViewHolder {
        TextView footerText;
        ProgressBar progressBar;
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
            footerText= itemView.findViewById(R.id.footer_text);
            progressBar= itemView.findViewById(R.id.pb_main_download);
            if(arriveBottom){
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
        } else  if(viewType==1){
            //底部“加载更多”item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footertext, parent, false);
                return new FooterHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_holder, parent, false);
            return new HeadHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            HomeTextItem homeTextItem= mList.get(position);
            (((ViewHolder) holder).textView1).setText("类型："+homeTextItem.getSuperChapterName()+"/"+homeTextItem.getChapterName());
            (((ViewHolder) holder).textView2).setText("时间："+homeTextItem.getNiceDate());
            (((ViewHolder) holder).textView3).setText(homeTextItem.getTitle());
            (((ViewHolder) holder).textView4).setText("作者："+homeTextItem.getShareUser());
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
        } else if(position==0){
            //加载HeadHolder
            return 2;
        }else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    //提供给外部调用的方法 刷新数据
    public void updateData(List<HomeTextItem> list){
        //再此处理获得的数据  list为传进来的数据
        //... list传进来的数据 添加到mList中
        if(list.isEmpty()){
            arriveBottom=true;
        }
        for (int i = 0; i < list.size(); i++) {
            mList.add(list.get(i));
        }
        //通知适配器更新
        notifyDataSetChanged();
    }

}
