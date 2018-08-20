package com.sz.jjj.thread.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.jjj.R;
import com.sz.jjj.thread.threadpool.ThreadPoolProxy;
import com.sz.jjj.thread.threadpool.ThreadPoolProxyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:jjj
 * @data:2018/8/20
 * @description:
 */

public class ExecutorPoolActivity extends Activity {

    ThreadPoolProxy threadPoolProxy;
    List<Runnable> mRunnables;
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            SystemClock.sleep(20000);
//            Log.e("-----", "newFixedThreadPool1");
//        }
//    };
//
//    Runnable runnable2 = new Runnable() {
//        @Override
//        public void run() {
//            SystemClock.sleep(20000);
//            Log.e("-----", "newFixedThreadPool2");
//        }
//    };
//
//    Runnable runnable3 = new Runnable() {
//        @Override
//        public void run() {
//            SystemClock.sleep(20000);
//            Log.e("-----", "newFixedThreadPool3");
//        }
//    };
//
//    Runnable runnable4 = new Runnable() {
//        @Override
//        public void run() {
//            SystemClock.sleep(20000);
//            Log.e("-----", "newFixedThreadPool4");
//        }
//    };

    private static class DelayRunnable implements Runnable{

        private String name;

        public DelayRunnable(String name){
            this.name=name;
        }

        @Override
        public void run() {
            SystemClock.sleep(20000);
            Log.e("-----", name);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRunnables=new ArrayList<>();
        mRunnables.add(new DelayRunnable("runnable1"));
        mRunnables.add(new DelayRunnable("runnable2"));
        mRunnables.add(new DelayRunnable("runnable3"));
        mRunnables.add(new DelayRunnable("runnable4"));
        // 一堆人排队上厕所，有固定个坑
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        // 一堆人排队上厕所，只有一个坑
//        ExecutorService fixedThreadPool = Executors.newSingleThreadExecutor();
        // 一堆人去大型咖啡店喝咖啡
//        ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
        // 一堆人去大型咖啡店喝咖啡
//        ExecutorService fixedThreadPool = Executors.newScheduledThreadPool(2);
//        for(Runnable runnable: mRunnables){
//            fixedThreadPool.execute(runnable);
//        }

        threadPoolProxy = ThreadPoolProxyFactory.getNormalThreadPoolProxy();
        for(Runnable runnable: mRunnables){
            threadPoolProxy.execute(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        for(Runnable runnable: mRunnables){
            threadPoolProxy.remove(runnable);
        }
        super.onDestroy();
    }
}
