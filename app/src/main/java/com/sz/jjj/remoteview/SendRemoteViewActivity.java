package com.sz.jjj.remoteview;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.sz.jjj.R;

/**
 * @author:jjj
 * @data:2018/8/24
 * @description:
 */

public class SendRemoteViewActivity extends AppCompatActivity {

    public static final String ACTION_CLICK = "com.sz.jjj.remoteview.remote.click";
    public static final String ACTION_REMOTE_VIEWS = "action_remote_views";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        // 不可用
        remoteViews.setImageViewResource(R.id.iv_icon, R.drawable.ic_launcher);
        remoteViews.setTextViewText(R.id.tv_title, "打开RemoteViewActivity");
        remoteViews.setTextViewText(R.id.tv_content, "msg from process:" + Process.myPid() + "\n" +
                "打开SendRemoteViewActivity");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, RemoteViewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
                new Intent(this, SendRemoteViewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_title, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.tv_content, pendingIntent2);

        Intent intent = new Intent(ACTION_CLICK);
        intent.putExtra(ACTION_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
    }
}
