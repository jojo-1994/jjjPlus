package com.sz.jjj.thread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sz.jjj.R;
import com.sz.jjj.baselibrary.util.ToastUtil;
import com.sz.jjj.thread.LocalIntentService;

/**
 * @author:jjj
 * @data:2018/8/20
 * @description:
 */

public class IntentServiceActivity extends AppCompatActivity implements LocalIntentService.UploadUI {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        LocalIntentService.setUploadUI(this);
        Intent service = new Intent(this, LocalIntentService.class);
        service.putExtra("task_action", "TASK1");
        startService(service);
        service.putExtra("task_action", "TASK2");
        startService(service);
    }

    @Override
    public void uploadUI(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = message.getData();
                String msg = bundle.getString("msg");
                ToastUtil.show(IntentServiceActivity.this, msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        LocalIntentService.setUploadUI(null);
        super.onDestroy();
    }
}
