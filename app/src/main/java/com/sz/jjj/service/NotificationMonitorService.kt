package com.sz.jjj.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

/**
 * Created by jjj on 2017/7/31.
@description: http://www.jianshu.com/p/82713b43b59e
 */
class NotificationMonitorService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val extras = sbn!!.getNotification().extras;
        // 获取接收消息APP的包名
        val notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        val notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        val notificationText = extras.getString(Notification.EXTRA_TEXT);

        Log.i("XSL_Test", "Notification posted " + notificationPkg + notificationTitle + " & " + notificationText);
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        val extras = sbn!!.getNotification().extras;
        // 获取接收消息APP的包名
        val notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        val notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        val notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("XSL_Test", "Notification removed " + notificationTitle + " & " + notificationText);
    }
}