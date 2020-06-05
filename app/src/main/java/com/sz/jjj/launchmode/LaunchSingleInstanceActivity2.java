package com.sz.jjj.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2018/3/30.
 *
 * @description:
 */

public class LaunchSingleInstanceActivity2 extends AppCompatActivity {
    @BindView(R.id.tv_des)
    TextView tvDes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        ButterKnife.bind(this);
        tvDes.setText(this.toString()+"\n"+"current task id:"+this.getTaskId());
    }

    @OnClick({R.id.btn_open1, R.id.btn_open2, R.id.btn_open3, R.id.btn_open4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open1:
                startActivity(new Intent(this, LaunchStardedActivity.class));
                break;
            case R.id.btn_open2:
                startActivity(new Intent(this, LaunchSingleTopActivity.class));
                break;
            case R.id.btn_open3:
                startActivity(new Intent(this, LaunchSingleTaskActivity.class));
                break;
            case R.id.btn_open4:
                startActivity(new Intent(this, LaunchSingleInstanceActivity.class));
                break;
        }
    }
}
