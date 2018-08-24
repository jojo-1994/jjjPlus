package com.sz.jjj.remoteview;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.sz.jjj.MainActivity;
import com.sz.jjj.R;
import com.sz.jjj.baselibrary.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/22
 * @description:
 */

public class RemoteViewActivity extends Activity {
    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.ll_remoteview_content)
    LinearLayout llRemoteviewContent;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteView = intent.getParcelableExtra(SendRemoteViewActivity.ACTION_REMOTE_VIEWS);
            if (remoteView != null) {
                View view = remoteView.apply(RemoteViewActivity.this, llRemoteviewContent);
                llRemoteviewContent.addView(view);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remoteview_activity);
        ButterKnife.bind(this);
        ToastUtil.show(this, Process.myPid()+"");

        IntentFilter intentFilter = new IntentFilter(SendRemoteViewActivity.ACTION_CLICK);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @OnClick({R.id.btn_notification, R.id.btn_notification2, R.id.btn_notification3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_notification:
                // 默认通知
                sendDefaultNotification();
                break;
            case R.id.btn_notification2:
                // 自定义通知
                sendCustomNotification();
                break;
            case R.id.btn_notification3:
                // 打开发送远程View界面
                startActivity(new Intent(this, SendRemoteViewActivity.class));
                break;
        }
    }

    private void sendDefaultNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentText("2222")
                .setContentTitle("111")
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    private void sendCustomNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.tv_title, "111");
        remoteViews.setTextViewText(R.id.tv_content, "222");
        remoteViews.setImageViewResource(R.id.iv_icon, R.drawable.ic_launcher);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_title, pendingIntent2);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentText("2222")
                .setContentTitle("111")
                .setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}
