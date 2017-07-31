package com.sz.jjj.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

/**
 * Created by jjj on 2017/7/31.
 *
 * @description:
 */

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "辅助点击";
    private static MyAccessibilityService service;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "事件--->" + event );

    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        service = this;
        //发送广播，已经连接上了
        Intent intent = new Intent("com.sz.jjj.service.ACCESSBILITY_CONNECT");
        sendBroadcast(intent);
        Toast.makeText(this, "已连接抢红包服务", Toast.LENGTH_SHORT).show();
    }
}
