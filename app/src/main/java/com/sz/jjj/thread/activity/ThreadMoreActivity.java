package com.sz.jjj.thread.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author:jjj
 * @data:2018/9/27
 * @description:
 */
public class ThreadMoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> list = Collections.synchronizedList(new ArrayList<String>());
        for (int i = 0; i < 5; i++) {
            list.add("aaa" + i);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list) {
                    for (String item : list) {
                        Log.e("-----遍历元素", item);
                        // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.remove(4);
                Log.e("-----remove元素", list.size() + "");
            }
        }).start();

    }
}
