package com.sz.jjj.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.jjj.R;
import com.sz.jjj.event.TestEvent;
import com.sz.jjj.fragment.TestFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jjj on 2018/1/2.
 *
 * @description:
 */

public class TestFragmentActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;
    private int i = 0;
    private DownloadTask downloadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdraghelper);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        replace();
        downloadTask = new DownloadTask();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.ll_parent, TestFragment.getInstance(Integer.toString(i)), "artist");
//        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(TestEvent event) {
        Toast.makeText(this, "收到信息了", Toast.LENGTH_SHORT).show();
        new DownloadTask().execute();
    }

    private void replace() {
        i++;
        text.setText(Integer.toString(i));
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment lastFragment = getFragmentManager().findFragmentByTag("artist");
        if (lastFragment != null) {
            fragmentTransaction.remove(lastFragment);
        }
        fragmentTransaction.replace(R.id.ll_parent, TestFragment.getInstance(Integer.toString(i)), "artist");
        fragmentTransaction.commitAllowingStateLoss();
    }

    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            for (int i = 20; i <= 100; i += 20) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("ss", "AsyncTask doInBackground result--progress-->" + i);
                publishProgress(i);
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.e("onProgressUpdate", "onProgressUpdate" + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.e("onPostExecute", "onPostExecute");
            if (result) {
                replace();
                Toast.makeText(TestFragmentActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TestFragmentActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("onCancelled", "onCancelled");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
//        downloadTask.cancel(true);
    }
}
