package com.sz.jjj.thread.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sz.jjj.R;
import com.sz.jjj.baselibrary.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/15
 * @description:
 */

public class AsyncTaskActivity extends Activity {
    @BindView(R.id.tv_download)
    Button tvDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_asynctask_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_download, R.id.tv_execute, R.id.tv_callable})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                new DownloadFileTask(this).execute(10);
                break;
            case R.id.tv_execute:
                new MyAsyncTask("AsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "1");
                new MyAsyncTask("AsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "2");
                new MyAsyncTask("AsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "3");
                new MyAsyncTask("AsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "4");
                new MyAsyncTask("AsyncTask#5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "5");
                break;
            case R.id.tv_callable:
                MyCallableClass task1 = new MyCallableClass(0);
                FutureTask<String> futureTask = new FutureTask<String>(task1);
                Thread thread = new Thread(futureTask);
                thread.start();
                try {
                    Log.e("-----", "task1: " + futureTask.get());
                } catch (Exception e) {
                    Log.e("-----", e.toString());
                }

                break;
        }
    }

    public static class DownloadThrea extends HandlerThread implements Handler.Callback{

        public DownloadThrea(String name) {
            super(name);
        }

        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    }
    private static class MyCallableClass implements Callable<String> {

        private int flag = 0;

        public MyCallableClass(int flag) {
            this.flag = flag;
        }

        @Override
        public String call() throws Exception {
            if (flag == 0) {
                return "value = 0";
            } else {
                return null;
            }
        }
    }


    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private String name = "AsyncTask";

        public MyAsyncTask(String name) {
            this.name = name;
        }

        @Override
        protected String doInBackground(String... strings) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.e("-----", strings[0] + "execute start at " + df.format(new Date()));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.e("-----", s + "execute finish at " + df.format(new Date()));
        }
    }

    private static class DownloadFileTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<AsyncTaskActivity> mActivity;

        public DownloadFileTask(AsyncTaskActivity mActivity) {
            this.mActivity = new WeakReference<AsyncTaskActivity>(mActivity);
        }

        @Override
        protected String doInBackground(final Integer... time) {
            int i = 0;
            while (i < time[0]) {
                publishProgress(i);
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mActivity.get() != null) {
                mActivity.get().tvDownload.setText(String.valueOf(values[0]));
            }
        }

        @Override
        protected void onPostExecute(String des) {
            if (mActivity.get() != null) {
                ToastUtil.show(mActivity.get(), des);
            }
        }
    }
}
