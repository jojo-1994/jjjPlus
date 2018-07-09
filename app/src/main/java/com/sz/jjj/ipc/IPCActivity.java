package com.sz.jjj.ipc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.jjj.R;
import com.sz.jjj.ipc.aidl.BookManagerActivity;
import com.sz.jjj.ipc.messenger.MessengerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2018/3/29.
 *
 * @description:
 */

public class IPCActivity extends AppCompatActivity {

    public static int processData = 0;
    @BindView(R.id.tv_static)
    TextView tvStatic;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_deserialize)
    TextView tvDeserialize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_activity);
        ButterKnife.bind(this);
        processData = 2;
        tvStatic.setText("静态变量：" + processData);
        Log.e("IpcActivity", "processData :" + processData);
        List<String> mList = new ArrayList();
    }

    @OnClick({R.id.btn_ipc1, R.id.btn_ipc2, R.id.btn_save, R.id.btn_serialize, R.id.btn_deserialize,
            R.id.btn_Parcelable, R.id.btn_messenger, R.id.btn_aidl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ipc1:
                startActivity(new Intent(this, IPC1Activity.class));
                break;
            case R.id.btn_ipc2:
                startActivity(new Intent(this, IPC2Activity.class));
                break;
            case R.id.btn_save:
                SharedPreferences settings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", "IPC");
                editor.commit();
                tvSave.setText("SharedPreferences存储成功：name：IPC");
                break;
            case R.id.btn_serialize:
                // 序列化
                // Serialize方式
                try {
                    IPCUser user = new IPCUser("0", "jake", "1");
                    ObjectOutput output = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(), "tours.txt")));
                    output.writeObject(user);
                    output.close();
                    Toast.makeText(this, "序列化成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_deserialize:
                //反序列化
                // Serialize方式
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(getFilesDir(), "tours.txt")));
                    IPCUser newUser = (IPCUser) inputStream.readObject();
                    inputStream.close();
                    tvDeserialize.setText("反序列化成功newUser:" + newUser.userNames);
                    Log.d("Ipc1Activity", "newUser:" + newUser.userNames);
                } catch (IOException e) {
                    tvDeserialize.setText("反序列化失败");
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_Parcelable:
                // 序列化
                // Parcelable方式
                IPCUserPar user = new IPCUserPar("0", "jake", "1");
                startActivity(new Intent(this, IPC2Activity.class).putExtra("user", user));
                break;
            case R.id.btn_messenger:
                // messenger
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case R.id.btn_aidl:
                startActivity(new Intent(this, BookManagerActivity.class));
                break;
            default:
        }
    }

}
