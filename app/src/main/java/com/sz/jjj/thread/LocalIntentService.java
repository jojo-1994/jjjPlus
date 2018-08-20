package com.sz.jjj.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author:jjj
 * @data:2018/8/20
 * @description:
 */

public class LocalIntentService extends IntentService {

    // 必须实现无参构造函数
    public LocalIntentService() {
        super("intentservice");
    }

    @Override
    public void onCreate() {
        Log.e("-------", "onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.e("-------", "onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("-------", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("-------", "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("-------", "onBind");
        return super.onBind(intent);
    }

    public interface UploadUI {
        void uploadUI(Message msg);
    }

    public static UploadUI mUploadUI;

    public static void setUploadUI(UploadUI uploadUI) {
        mUploadUI = uploadUI;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.e("-------", action);
        SystemClock.sleep(30000);
        if (action.equals("TASK1")) {
            Log.e("-------2", "TASK1");
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg", "可以执行界面操作了");
            msg.setData(bundle);
            if (mUploadUI != null) {
                mUploadUI.uploadUI(msg);
            }
        }
    }
}
