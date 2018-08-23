package com.sz.jjj.remoteview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.sz.jjj.MainActivity;
import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/22
 * @description:
 */

public class RemoteViewActivity extends AppCompatActivity {
    @BindView(R.id.btn_notification)
    Button btnNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remoteview_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_notification, R.id.btn_notification2})
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
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    private void sendCustomNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.notification_layout);
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
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}
