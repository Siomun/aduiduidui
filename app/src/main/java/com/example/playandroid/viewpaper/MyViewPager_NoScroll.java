package com.example.playandroid.viewpaper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class MyViewPager_NoScroll extends ViewPager {
    public MyViewPager_NoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        float mDx=0.0f, mDy=0.0f;//按下坐标值
        //判断子item总个数不大于1，TouchEvent返回默认值，继续消费。
        if (getChildCount() <= 1) {
            super.onTouchEvent(ev);
        }
        //获取事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                mDx = ev.getX();
                mDy = ev.getY();
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
                }
                break;

            case MotionEvent.ACTION_MOVE://移动
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
                }
                break;

            case MotionEvent.ACTION_CANCEL://非人工操作
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
                }
                break;

            case MotionEvent.ACTION_UP://抬起
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
                }
                break;
        }

        return true;        //让事件不再分发
    }

}
