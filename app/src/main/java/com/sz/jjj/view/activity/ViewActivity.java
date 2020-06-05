package com.sz.jjj.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.jjj.R;
import com.sz.jjj.baselibrary.util.DisplayUtil;
import com.sz.jjj.baselibrary.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/7/13
 * @description:
 */

public class ViewActivity extends Activity {
    @BindView(R.id.tv_view)
    TextView tvView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        ButterKnife.bind(this);
        ViewGroup frameLayout=findViewById(android.R.id.content);
        if(frameLayout instanceof LinearLayout){
            ToastUtil.show(this, "LinearLayout");
        }else if(frameLayout instanceof FrameLayout){
            ToastUtil.show(this, "FrameLayout");
        }
        ViewGroup.LayoutParams params=frameLayout.getLayoutParams();
        params.width=ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        frameLayout.setLayoutParams(params);
        LinearLayout layout= (LinearLayout) frameLayout.getChildAt(0);
        frameLayout.setBackgroundColor(Color.GREEN);
        final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.e("ViewActivity", "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onLongPress");

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.e("ViewActivity", "onFling");
                return false;
            }
        });
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onSingleTapConfirmed");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onDoubleTap");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                Log.e("ViewActivity", "onDoubleTapEvent");
                return false;
            }
        });
        gestureDetector.setIsLongpressEnabled(false);
        tvView.setFocusable(true);
        tvView.setClickable(true);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.e("ViewActivity", "top:" + DisplayUtil.px2dp(this, tvView.getTop()));
            Log.e("ViewActivity", "right:" + DisplayUtil.px2dp(this, tvView.getRight()));
            Log.e("ViewActivity", "bottom:" + DisplayUtil.px2dp(this, tvView.getBottom()));
            Log.e("ViewActivity", "left:" + DisplayUtil.px2dp(this, tvView.getLeft()));
            Log.e("ViewActivity", "width:" + DisplayUtil.px2dp(this, tvView.getMeasuredWidth()));
            Log.e("ViewActivity", "height:" + DisplayUtil.px2dp(this, tvView.getMeasuredHeight()));
            Log.e("ViewActivity", "translationX:" + DisplayUtil.px2dp(this, tvView.getTranslationX()));
            Log.e("ViewActivity", "translationY:" + DisplayUtil.px2dp(this, tvView.getTranslationY()));
            Log.e("ViewActivity", "getX:" + DisplayUtil.px2dp(this, tvView.getX()));
            Log.e("ViewActivity", "getY:" + DisplayUtil.px2dp(this, tvView.getY()));
            Log.e("ViewActivity", "touch slop:" + DisplayUtil.px2dp(this, ViewConfiguration.get(this).getScaledTouchSlop()));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000);
        int xVelocityTracker = (int) velocityTracker.getXVelocity();
        int yVelocityTracker = (int) velocityTracker.getYVelocity();
//        Log.e("ViewActivity", "xVelocityTracker:" + xVelocityTracker);
//        Log.e("ViewActivity", "yVelocityTracker:" + yVelocityTracker);
        velocityTracker.clear();
        velocityTracker.recycle();
        return super.onTouchEvent(event);

    }

    @OnClick(R.id.tv_scroll)
    public void onViewClicked() {
        startActivity(new Intent(this, ScrollActivity.class));
    }
}
