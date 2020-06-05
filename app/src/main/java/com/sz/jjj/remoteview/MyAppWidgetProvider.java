package com.sz.jjj.remoteview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.sz.jjj.R;

/**
 * @author:jjj
 * @data:2018/8/23
 * @description:
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String CLICK_ACTION = "com.sz.jjj.remoteview.action.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e("----------", "onReceive----" + intent.getAction());
        if (intent.getAction().equals(CLICK_ACTION)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim_music_iv);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 360; i++) {
                        float degree = i % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.icon, remoteBitmap(context, bitmap, degree));
                        Intent intentClick = new Intent();
                        // 解决旋转混乱问题
                        intentClick.setAction(i == 359 ? CLICK_ACTION : AppWidgetManager.ACTION_APPWIDGET_DISABLED);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                                intentClick, PendingIntent.FLAG_ONE_SHOT);
                        remoteViews.setOnClickPendingIntent(R.id.icon, pendingIntent);
                        manager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
                        SystemClock.sleep(3);
                        Log.e("---", i + "");
                    }
                }
            }).start();
        } else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_DELETED)) {

        } else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_DISABLED)) {

        } else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED)) {

        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e("----------", "onUpdate----");
        final int counter = appWidgetIds.length;
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdata(context, appWidgetManager, appWidgetId);
        }
    }

    private void onWidgetUpdata(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteview = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intent = new Intent();
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteview.setOnClickPendingIntent(R.id.icon, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteview);
    }

    private Bitmap remoteBitmap(Context context, Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return tmpBitmap;
    }
}
