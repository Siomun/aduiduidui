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

    public class HeadHolder extends RecyclerView.ViewHolder implements IView2,IView{
        Presenter presenter;
        public HeadHolder(@NonNull View itemView) {
            super(itemView);
            rootView=itemView;
            presenter=new Presenter(this,this);
            presenter.fetchGetBannerData();
        }
        @SuppressWarnings("unchecked")
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void showData2(ArrayList<?> list) {
            mBannerItemList=(List<BannerItem>)list;
            mViewPager=rootView.findViewById(R.id.home_view_paper);
            mTextView=rootView.findViewById(R.id.home_banner_text);
            mImageView=new ImageView(itemView.getContext());
            mImageView.setImageBitmap(mBannerItemList.get(2).getBitmap());
            mImageViewList.add(mImageView);
            mBannerTitle.add(mBannerItemList.get(2).getTitle());
            mBannerLink.add(mBannerItemList.get(2).getUrl());
            for (int i = 0; i < mBannerItemList.size(); i++) {
                mImageView=new ImageView(itemView.getContext());
                mImageView.setImageBitmap(mBannerItemList.get(i).getBitmap());
                mImageViewList.add(mImageView);
                mBannerTitle.add(mBannerItemList.get(i).getTitle());
                mBannerLink.add(mBannerItemList.get(i).getUrl());
            }
            mImageView=new ImageView(itemView.getContext());
            mImageView.setImageBitmap(mBannerItemList.get(0).getBitmap());
            mImageViewList.add(mImageView);
            mBannerTitle.add(mBannerItemList.get(0).getTitle());
            mBannerLink.add(mBannerItemList.get(0).getUrl());
            mTextView.setText(mBannerItemList.get(2).getTitle());
            BannerAdapter bannerAdapter=new BannerAdapter(mImageViewList);
            mViewPager.setAdapter(bannerAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    //  * 真实数据：      0       1       2
                    //  * 实际数据：2     0       1       2       0
                    //  最后一个0切换到前面的0页面位置
                    if (position == mImageViewList.size() - 1) {
                        currentPosition =1;

                    } else if (position == 0) {
                        currentPosition = mImageViewList.size() - 2;
                    } else {
                        currentPosition = position;
                    }
                    mTextView.setText(mBannerTitle.get(position));
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // 页面滑动静止状态，偷天换日，换位置
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        // smoothScroll: 设置平稳滑动
                        mViewPager.setCurrentItem(currentPosition, false);
                    }
                }
            });
            if (beginCarousel){
                mHandler.sendEmptyMessageDelayed(0, 1000*2);
            }
           mViewPager.setOnTouchListener(new View.OnTouchListener() {
               float Dx;
               float Mx;
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            beginCarousel=false;
                            mHandler.removeCallbacksAndMessages(null);
                            viewPaperClick=0;
                            Dx=motionEvent.getX();
                            System.out.println("tou"+Dx);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Mx=motionEvent.getX();
                            System.out.println("tou"+Mx);
                            float move=Mx-Dx;
                            System.out.println("tou:move"+move);
                            if (move < -100) {
                                viewPaperClick = 1;
                            }else if(move>100){
                                viewPaperClick = 1;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            mHandler.sendEmptyMessageDelayed(0, 900*2);
                            beginCarousel=false;
                            if(viewPaperClick==0){
                                int item= mViewPager.getCurrentItem();
                                String data=mBannerLink.get(item);
                                String title=mBannerTitle.get(item);
                                Intent intent=new Intent(itemView.getContext(), WebViewClick.class);//给后面开启的活动传值
                                intent.putExtra("link",data);
                                intent.putExtra("title",title);
                                rootView.getContext().startActivity(intent);
                            }
                    }
                    return false;
                }
            });
        }

        @Override
        public void showData(ArrayList<?> list) {

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
