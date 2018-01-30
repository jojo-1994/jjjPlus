package com.sz.jjj.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.jjj.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2017/11/27.
 *
 * @description: 蓝牙demo
 */

public class BlueToothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.btn_open_bluetooth)
    Button btnOpenBluetooth;
    private ListViewAdapter adapter;
    private static final int REQUEST_PERMISSION_ACCESS_LOCATION = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private final String TAG = "BlueToothActivity";
    private ArrayList<BluetoothDevice> strArr = new ArrayList<>();
    private BluetoothReceiver bluetoothReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        ButterKnife.bind(this);

        // 判断是否支持蓝牙开发
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持蓝牙开发", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        bluetoothReceiver = new BluetoothReceiver();
        registerReceiver(bluetoothReceiver, intentFilter);

        adapter = new ListViewAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        setStatusBar(btnOpenBluetooth);
    }

    @OnClick({R.id.btn_open_bluetooth, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_bluetooth:
                // 开启蓝牙
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 0);
                }
                break;
            case R.id.btn_search:
                // 搜索设备
                if (requestPermission()) {
                    search();
                }
                break;
        }
    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkAccessFinePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkAccessFinePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_ACCESS_LOCATION);
                Log.e(getPackageName(), "没有权限，请求权限");
                return false;
            }
            Log.e(getPackageName(), "已有定位权限");
        }
        return true;
    }

    public void search() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        strArr.clear();
        mBluetoothAdapter.startDiscovery();
        Log.e(getPackageName(), "开始搜索");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void setStatusBar(final View view) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setPadding(0, getStatusBarHeight() + view.getPaddingTop(), 0, view.getPaddingBottom());
                }
            });
        }
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int statusBarHeight = getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
                Log.e("BluetoothReceiver", "找到新设备了");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                boolean addFlag = true;
                for (BluetoothDevice bluetoothDevice : strArr) {
                    if (device.getAddress().equals(bluetoothDevice.getAddress())) {
                        addFlag = false;
                    }
                }

                if (addFlag) {
                    strArr.add(device);
//                    Log.e("BluetoothReceiver", device.getName());
                    adapter.notifyDataSetChanged();
                }

            } else if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_NONE:
                        Log.e("BluetoothReceiver", "取消配对");
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        Log.e("BluetoothReceiver", "配对中");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.e("BluetoothReceiver", "配对成功");
                        break;
                }
            }
        }
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return strArr.size();
        }

        @Override
        public Object getItem(int i) {
            return strArr.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(BlueToothActivity.this).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            BluetoothDevice device = strArr.get(i);
            ((TextView) view).setText(device.getName() + "-----" + (device.getBondState() == BluetoothDevice.BOND_BONDED ? "已绑定" : "未绑定"));
            return view;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(getPackageName(), "开启权限permission granted!");
                    //做下面该做的事
                    search();
                } else {
                    Log.e(getPackageName(), "没有定位权限，请先开启!");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(bluetoothReceiver);
        super.onDestroy();
    }
}
