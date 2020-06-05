package com.sz.jjj.ipc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sz.jjj.ipc.IPCActivity.processData;

/**
 * Created by jjj on 2018/3/29.
 *
 * @description:
 */

public class IPC2Activity extends AppCompatActivity {
    @BindView(R.id.tv_static)
    TextView tvStatic;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_user)
    TextView tvUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc1_activity);
        ButterKnife.bind(this);
        tvStatic.setText("静态变量：" + processData);
        Log.e("Ipc1Activity", "processData :" + processData);
    }

    @OnClick({R.id.btn_save, R.id.btn_deserialize})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                SharedPreferences settings = getSharedPreferences("setting", 0);
                String name = settings.getString("name", "默认值");
                tvData.setText(name);
                Log.d("Ipc2Activity", "name:" + name);
                break;
            case R.id.btn_deserialize:
                //反序列化
                IPCUserPar userPar= (IPCUserPar) getIntent().getExtras().get("user");
                tvUser.setText("newUser:" + userPar.userNames);
                Log.d("Ipc1Activity", "newUser:" + userPar.userNames);
                break;
        }

    }
}
