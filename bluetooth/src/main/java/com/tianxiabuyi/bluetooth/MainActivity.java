package com.tianxiabuyi.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.taidoc.pclinklibrary.android.bluetooth.util.BluetoothUtil;
import com.taidoc.pclinklibrary.interfaces.BleUtilsListener;
import com.taidoc.pclinklibrary.util.BleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:jjj
 * @data:2018/6/5
 * @description:
 */

public class MainActivity extends AppCompatActivity {

    private Button mSearch;
    private RecyclerView recyclerview;

    private ConnectPresenter mPresenter;

    private MainAdapter mainAdapter;
    private BluetoothAdapter mAdapter;
    private BleUtils mBleUtils;

    private List<BluetoothDevice> mSearchedDevices;

    private Map<String, String> mPairedMeterNames;
    private Map<String, String> mPairedMeterAddrs;
    private Map<String, String> mBackupPairedMeterNames;
    private Map<String, String> mBackupPairedMeterAddrs;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    public void onStart() {
        super.onStart();

        initValue();
        backupData();

        mPresenter.connectMeter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = findViewById(R.id.btn_search);
        recyclerview = findViewById(R.id.recyclerview);

        mAdapter = BluetoothUtil.getBluetoothAdapter();
        mBleUtils = new BleUtils(mAdapter, mBleUtilsListener);
        mBleUtils.initScanner();

//        mPresenter = new ConnectPresenter(this, "C0:26:DF:03:56:17");// 血糖
        mPresenter = new ConnectPresenter(this, "C0:26:DA:00:2D:81");// 血压
    }

    public void search(View view) {
        if (mSearch.getTag().toString().equals("0")) {
            mSearch.setTag("1");
            mSearch.setText(R.string.setting_meter_stop);

            if (mSearchedDevices.size() > 0) {
                mSearchedDevices.clear();
            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    processScanDeviceTimeout();
                }
            }, SCAN_PERIOD);

            mBleUtils.scanLeDevice(true);

        } else {
            processScanDeviceTimeout();
        }
    }

    public void connect(View view) {
        mPresenter.connectMeter();
    }

    public void latestData(View view){
        mPresenter.getLastData();
    }

    public void allData(View view){
        mPresenter.getAllData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.disconnectMeter();
    }

    private void initValue() {
        mSearchedDevices = new ArrayList<BluetoothDevice>();
        mPairedMeterNames = new HashMap<String, String>();
        mPairedMeterAddrs = new HashMap<String, String>();

        // 适配器
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(mSearchedDevices);
        recyclerview.setAdapter(mainAdapter);
    }

    private void backupData() {

    }

    private BleUtilsListener mBleUtilsListener = new BleUtilsListener() {
        @Override
        public void onScanned(BluetoothDevice device, int rssi) {
            if (device != null && !TextUtils.isEmpty(device.getName())) {
                if (!mPairedMeterAddrs.containsValue(device.getAddress()) &&
                        !mSearchedDevices.contains(device)) {
                    mSearchedDevices.add(device);
                    mainAdapter.notifyDataSetChanged();
                    Log.e("dddddd", device.getName() + "--" + device.getAddress());
                }
            }
        }

        @Override
        public void onLost(BluetoothDevice device) {
            if (device != null && !TextUtils.isEmpty(device.getName())) {
                if (mSearchedDevices.contains(device)) {
                    mSearchedDevices.remove(device);
                    mainAdapter.notifyDataSetChanged();
                    Log.e("zzzzzz", device.getName());
                }
            }
        }
    };

    private void processScanDeviceTimeout() {
        mBleUtils.scanLeDevice(false);
        mSearch.setTag("0");
        mSearch.setText(R.string.meter_search);
    }
}
