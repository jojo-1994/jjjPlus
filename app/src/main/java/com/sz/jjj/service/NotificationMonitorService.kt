package com.sz.jjj.service

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.content.Intent
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.support.annotation.RequiresApi
import android.util.Log
import com.sz.jjj.Config

/**
 * Created by jjj on 2017/7/31.
@description: http://www.jianshu.com/p/82713b43b59e
 */
class NotificationMonitorService : NotificationListenerService() {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        @SuppressLint("NewApi")
        val extras = sbn!!.getNotification().extras;
        // 获取接收消息APP的包名
        val notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        val notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        val notificationText = extras.getString(Notification.EXTRA_TEXT);

        Log.i(Config.TAG, "Notification posted " + notificationPkg + notificationTitle + " & " + notificationText);

        val intent = Intent("auto.click")
        intent.putExtra("flag", 1)
//                    intent.putExtra("id","bt_accessibility");
        intent.putExtra("id", "监控器开关已打开")
//        intent.putExtra("id", "联系人")

        sendBroadcast(intent)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        val extras = sbn!!.getNotification().extras;
        // 获取接收消息APP的包名
        val notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        val notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        val notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i(Config.TAG, "Notification removed " + notificationTitle + " & " + notificationText);
    }
}