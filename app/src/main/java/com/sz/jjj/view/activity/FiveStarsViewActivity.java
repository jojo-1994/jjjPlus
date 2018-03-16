package com.sz.jjj.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sz.jjj.R;
import com.sz.jjj.view.widget.StartBar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jjj on 2017/11/2.
 *
 * @description: 五星点评
 */

public class FiveStarsViewActivity extends AppCompatActivity {

    private int minute = 30;//这是分钟
    private int second = 0;//这是分钟后面的秒数。这里是以30分钟为例的，所以，minute是30，second是0
    private TextView timeView;
    /**
     * 使用工厂方法初始化一个ScheduledThreadPool
     */
    ScheduledExecutorService newScheduledThreadPool;
    ScheduledFuture scheduledFuture;
    private Runnable runnable;
    //这是接收回来处理的消息
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (minute == 0 && second == 0) {
                stopThreadPool();
                return;
            }
            if (second == 0) {
                second = 59;
                minute--;
            } else {
                second--;
            }
            timeView.setText(String.format("%02d", minute) + "：" + String.format("%02d", second));
        }

    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_five_stars_activity);
        StartBar startBar = findViewById(R.id.startBar);
        final TextView tvDes = findViewById(R.id.tvDes);
        startBar.setOnStarChangeListener(new StartBar.OnStarChangeListener() {
            @Override
            public void onStarChange(float mark) {
                String des = "";
                int start = (int) Math.ceil(mark);
                if (start == 1) {
                    des = "非常差";
                } else if (start == 2) {
                    des = "差";
                } else if (start == 3) {
                    des = "一般";
                } else if (start == 4) {
                    des = "好";
                } else if (start == 5) {
                    des = "非常好";
                }
                tvDes.setText(des);
            }
        });

        // 30分钟倒计时
        timeView = (TextView) findViewById(R.id.tv);
        timeView.setText(minute + ":" + second);
        runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
                System.out.println("welcome to china" + Thread.currentThread().getName());
            }
        };
        newScheduledThreadPool = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("ddddd").build());
        scheduledFuture = newScheduledThreadPool.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);
    }


    @Override
    protected void onDestroy() {
        stopThreadPool();
        super.onDestroy();
    }

    /**
     * 线程池
     */
    private void stopThreadPool() {
        if(scheduledFuture != null){
            scheduledFuture.cancel(true);
            scheduledFuture=null;
        }
        if (newScheduledThreadPool != null) {
            newScheduledThreadPool.shutdown();
            newScheduledThreadPool = null;
        }
        if (runnable != null) {
            runnable = null;
        }
        minute = -1;
        second = -1;
    }

}
