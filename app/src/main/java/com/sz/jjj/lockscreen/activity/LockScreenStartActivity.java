package com.sz.jjj.lockscreen.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.sz.jjj.MainActivity;
import com.sz.jjj.R;
import com.sz.jjj.lockscreen.impl.IMainView;
import com.sz.jjj.lockscreen.impl.ScreenPresenter;

/**
 * Created by jjj on 2017/9/6.
 *
 * @description:
 */

public class LockScreenStartActivity extends AppCompatActivity implements IMainView {
    private ScreenPresenter mPresenter;
    private String TAG = "LockScreenActivity";
    BroadcastReceiver mBatInfoReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen_time_activity);
        mPresenter = new ScreenPresenter(this);
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                Log.e(TAG, "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.e(TAG, "screen on");
                    startActivity(new Intent(LockScreenStartActivity.this, MainActivity.class));
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.e(TAG, "screen off");
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.e(TAG, "screen unlock");
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.e(TAG, " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                }
            }
        };
        Log.e(TAG, "registerReceiver");
        registerReceiver(mBatInfoReceiver, filter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重置计时
        mPresenter.resetTipsTimer();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPresenter.resetTipsTimer();
        Log.e("touch", "touch");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        //启动默认开始计时
        mPresenter.startTipsTimer();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //有其他操作时结束计时
        mPresenter.endTipsTimer();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.endTipsTimer();
        unregisterReceiver(mBatInfoReceiver);
    }

    @Override
    public void showTipsView() {
        //展示屏保界面
        Intent intent = new Intent(LockScreenStartActivity.this, LockScreenActivity.class);
        startActivity(intent);
    }

}
