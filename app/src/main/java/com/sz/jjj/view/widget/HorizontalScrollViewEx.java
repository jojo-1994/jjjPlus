package com.sz.jjj.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by jiangjiaojiao on 2018/7/25.
 */

public class HorizontalScrollViewEx extends ViewGroup {

    private Scroller mScroller;
    //速度追踪
    private VelocityTracker mVelocityTracker;


    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mScroller=new Scroller(getContext());
        mVelocityTracker=VelocityTracker.obtain();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted=false;
        int x= (int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){

        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
