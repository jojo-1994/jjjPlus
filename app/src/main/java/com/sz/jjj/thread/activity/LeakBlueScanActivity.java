package com.sz.jjj.thread.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.sz.jjj.R;
import com.sz.jjj.baselibrary.util.ToastUtil;
import com.sz.jjj.thread.LeCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/10
 * @description:
 */

public class LeakBlueScanActivity extends Activity {

    private LeCallback leCallback;
    private BluetoothLeScanner scanner;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_bluescan_activity);
        ButterKnife.bind(this);

        leCallback = new LeCallback(new LeCallback.LeCallbackInterface() {
            @Override
            public void onScanResult(ScanResult result) {
                Log.e("------", "发现设备");
            }
        });
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        scanner = mBluetoothAdapter.getBluetoothLeScanner();
        mBluetoothAdapter.enable();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        // todo 注意开启定位权限
        scanner.stopScan(leCallback);
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.start_scan)
    public void onViewClicked() {
        ToastUtil.show(this, "开始扫描");
        scanner.startScan(leCallback);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private final static class LeCallback extends ScanCallback {
//
//        private WeakReference<LeakHandlerActivity> mBlueToothScanUtils;
//
//        public LeCallback(LeakHandlerActivity activity) {
//            this.mBlueToothScanUtils = new WeakReference(activity);
//        }
//
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            Log.e("------", "发现设备");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                BluetoothDevice device = result.getDevice();
//                LeakHandlerActivity blue = mBlueToothScanUtils.get();
//                if (blue != null) {
////                    Log.e("------", "发现设备");
//                }
//            }
//        }
//
//        @Override
//        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
//        }
//    }
}
