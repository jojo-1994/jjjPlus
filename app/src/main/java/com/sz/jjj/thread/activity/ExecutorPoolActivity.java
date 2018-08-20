package com.sz.jjj.thread.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.jjj.R;
import com.sz.jjj.thread.threadpool.ThreadPoolProxy;
import com.sz.jjj.thread.threadpool.ThreadPoolProxyFactory;

/**
 * @author:jjj
 * @data:2018/8/20
 * @description:
 */

public class ExecutorPoolActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Log.e("-----", "newFixedThreadPool1");
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Log.e("-----", "newFixedThreadPool2");
            }
        };

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Log.e("-----", "newFixedThreadPool3");
            }
        };

        Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Log.e("-----", "newFixedThreadPool4");
            }
        };

        // 一堆人排队上厕所，有固定个坑
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        // 一堆人排队上厕所，只有一个坑
//        ExecutorService fixedThreadPool = Executors.newSingleThreadExecutor();
        // 一堆人去大型咖啡店喝咖啡
//        ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
        // 一堆人去大型咖啡店喝咖啡
//        ExecutorService fixedThreadPool = Executors.newScheduledThreadPool(2);
//        fixedThreadPool.execute(runnable);
//        fixedThreadPool.execute(runnable2);
//        fixedThreadPool.execute(runnable3);
//        fixedThreadPool.execute(runnable4);

        ThreadPoolProxy threadPoolProxy = ThreadPoolProxyFactory.getNormalThreadPoolProxy();
        threadPoolProxy.execute(runnable);
        threadPoolProxy.execute(runnable2);
        threadPoolProxy.execute(runnable3);
        threadPoolProxy.execute(runnable4);
    }

}
