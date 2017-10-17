package com.sz.jjj.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.sz.jjj.Config;
import com.sz.jjj.util.NotifyHelper;

import java.util.List;

/**
 * Created by jjj on 2017/7/31.
 *
 * @description:
 */

public class MyAccessibilityService extends AccessibilityService {

    private static MyAccessibilityService service;
    private NotificationReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Config.TAG, "onCreate" );
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(Config.TAG, "事件1--->" + event );
        Log.d(Config.TAG, "事件2--->" + event.getEventType() );
        int eventType = event.getEventType();
        //根据事件回调类型进行处理
        switch (eventType) {
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.d(Config.TAG, "事件1--->当通知栏发生改变时"  );
                Parcelable data = event.getParcelableData();
                if(data == null || !(data instanceof Notification)) {
                    return;
                }

                List<CharSequence> texts = event.getText();
                if(!texts.isEmpty()) {
                    String text = String.valueOf(texts.get(0));
                    notificationEvent(text, (Notification) data);
                }
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d(Config.TAG, "事件1--->当窗口的状态发生改变时"  );
                performClick("com.tencent.mobileqq:id/ivTitleBtnRightImage");
                break;
        }

    }

    /** 通知栏事件*/
    private void notificationEvent(String ticker, Notification nf) {
        Log.e(Config.TAG, ticker);
        if(ticker.contains("我的电脑")) { //红包消息
            Log.e(Config.TAG, ticker);
            newHongBaoNotification(nf);
        }
    }


    /** 打开通知栏消息*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void newHongBaoNotification(Notification notification) {

        //以下是精华，将微信的通知栏消息打开
        PendingIntent pendingIntent = notification.contentIntent;
        boolean lock = NotifyHelper.isLockScreen(this.getApplication());

        if(!lock) {
            NotifyHelper.send(pendingIntent);
        } else {
            NotifyHelper.showNotify(this.getApplicationContext(), String.valueOf(notification.tickerText), pendingIntent);
        }

//        if(lock || getConfig().getWechatMode() != Config.WX_MODE_0) {
//            NotifyHelper.playEffect(this.getApplicationContext(), getConfig());
//        }
    }

    public Config getConfig() {
        return service.getConfig();
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        Log.e("ddddddddddddddddd", "MyAccessibilityService connected!");
        super.onServiceConnected();
        service = this;
        //发送广播，已经连接上了
        Intent intent = new Intent(Config.ACTION_MyACCESSITITLITY_SERVICE_CONNECT);
        sendBroadcast(intent);
        Toast.makeText(this, "已连接抢红包服务", Toast.LENGTH_SHORT).show();

        Log.i("mService","点击执行");

        if (receiver == null) {
            receiver = new NotificationReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("auto.click");
            registerReceiver(receiver, intentFilter);
        }
    }


    //执行点击
    private void performClick(String resourceId) {

        Log.i(Config.TAG,"点击执行");

        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
//        targetNode = findNodeInfosById(nodeInfo,"com.youmi.android.addemo:id/"+resourceId);
        targetNode = findNodeInfosById(nodeInfo,resourceId);
        if (targetNode!=null && targetNode.isClickable()) {
            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }


    //通过id查找
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    //通过文本查找
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int i = intent.getIntExtra("flag", 0);
            Log.i(Config.TAG, "广播flag=" + i);
            if (i == 1) {
                String resourceid = intent.getStringExtra("id");
                performClick(resourceid);
            }
        }
    }
}
