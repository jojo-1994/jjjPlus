package com.sz.jjj.ble.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.jjj.R;
import com.sz.jjj.ble.adapter.BLTAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2017/11/27.
 *
 * @description: 蓝牙demo
 */

public class BlueToothActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.btn_open_bluetooth)
    Button btnOpenBluetooth;
    @BindView(R.id.btn_state)
    TextView btnState;
    private BLTAdapter adapter;
    private static final int REQUEST_PERMISSION_ACCESS_LOCATION = 1;
    private BluetoothAdapter bleAdapter;
    private final String TAG = "BlueToothActivity";
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();
    private ArrayList<String> strList = new ArrayList<>();
    private BluetoothLeScanner scanner;
    //从扫描到的设备列表里选出目标设备。
    private BluetoothDevice mTargetDevice;

    private final UUID SERVICE_UUID = UUID.fromString("00001523-1212-efde-1523-785feabcd123");
    private final UUID SERVICE_UUID2 = UUID.fromString("00001524-1212-efde-1523-785feabcd123");
    private BluetoothGattService service;
    private BluetoothGattCharacteristic mBluetoothGattChar;
    private BluetoothGatt mBluetoothGatt;

    // 蓝牙扫描回调接口
    private ScanCallback leCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BluetoothDevice device = result.getDevice();
                //判断是否已经添加
                if (!devices.contains(device)) {
                    devices.add(device);
                    strList.add(device.getName() + "--" + device.getName() + "--" + device.getAddress() + (device.getBondState() == BluetoothDevice.BOND_BONDED ? "已绑定" : "未绑定"));
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e(TAG, "搜索失败");
        }
    };

    // 蓝牙通信回调接口
    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {

        /**
         * 该方法运行在一个Binder 线程里， 不建议处理耗时操作
         * @param gatt
         * @param status 之前的状态
         * @param newState 当前最新状态
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.e(TAG, "onConnectionStateChange-----" + "status:" + status + "----newState:" + newState);
            if (status != BluetoothGatt.GATT_SUCCESS) {
                // 当尝试连接失败的时候调用 disconnect 方法是不会引起这个方法回调的，所以这里直接回调就可以了。
                gatt.close();
                Log.e(TAG, "Cannot connect device with error status:");
                return;
            }

            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    //这里表示已经成功连接，如果成功连接，我们就会执行discoverServices()方法去发现设备所包含的服务
                    gatt.discoverServices();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnState.setText("连接成功");
                        }
                    });
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    //表示gatt连接已经断开。
                    gatt.close();
                    break;
            }

        }

        // writeCharacteristic()写入成功，会在此有反馈
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.e(TAG, gatt.getDevice().getName() + " write successfully");
            String response = byteArrayToStr(characteristic.getValue());
            Log.e(TAG, gatt.getDevice().getName() + " write successfully" + response);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnState.setText(" write successfully");
                }
            });
        }

        // readCharacteristic()读取成功，会在此有反馈
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.e(TAG, gatt.getDevice().getName() + " recieved read status");
            String value = byteArrayToStr(characteristic.getValue());
        }

        // writeCharacteristic()写入成功并且服务端蓝牙设备有反馈，结果会回调在此
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            byte[] bytes = characteristic.getValue();
            if (bytes == null) {
                return;
            }
            Log.e(TAG, "The response is Changed");
            String response = byteArrayToStr(characteristic.getValue());
        }

        // 发现服务成功
        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            // 成功订阅
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //gatt.getServices()可以获得外设的所有服务。
                final List<BluetoothGattService> services = gatt.getServices();
                if (services == null) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (BluetoothGattService service : services) {
                            Log.e(TAG, "----- service uuid : " + service.getUuid().toString() + " --");
                            strList.add(service.getUuid().toString());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                // 初始化通信的通道
                service = gatt.getService(SERVICE_UUID);
                mBluetoothGattChar = service.getCharacteristic(SERVICE_UUID2);
                BluetoothGattDescriptor mDescriptor = mBluetoothGattChar.getDescriptors().get(0);
                // 启动通知值，写入或读取数据时onCharacteristicChanged（）才会有反馈
                mDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                SystemClock.sleep(200);
                gatt.setCharacteristicNotification(mBluetoothGattChar, true);
                gatt.writeDescriptor(mDescriptor);
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blue_tooth_activity);
        ButterKnife.bind(this);

        // 判断是否支持蓝牙开发
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持蓝牙开发", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取 BluetoothAdapter
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bleAdapter = bluetoothManager.getAdapter();
        scanner = bleAdapter.getBluetoothLeScanner();

        // 初始化列表
        adapter = new BLTAdapter(this, strList);
        list.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.btn_open_bluetooth, R.id.btn_search, R.id.btn_disconnect, R.id.btn_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_bluetooth:
                // 开启蓝牙
                if (bleAdapter == null || !bleAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 0);
                }
                break;
            case R.id.btn_search:
                // 蓝牙扫描
                if (requestPermission()) {
                    startScan();
                }
                break;
            case R.id.btn_disconnect:
                // 断开连接
                disconnect();
                break;
            case R.id.btn_write:
                // 读取操作
                if (mBluetoothGatt != null) {
                    byte[] sendValue = new byte[]{0x51, 0x27, 0x00, 0x00, 0x00, 0x00, (byte) 0xa3, (byte) 0x1b};
                    mBluetoothGattChar.setValue(sendValue);
                    mBluetoothGatt.writeCharacteristic(mBluetoothGattChar);
                }
                break;
            default:
        }
    }

    // 6.0 权限请求
    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkAccessFinePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkAccessFinePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_ACCESS_LOCATION);
                Log.e(getPackageName(), "没有权限，请求权限");
                return false;
            }
            Log.e(getPackageName(), "已有定位权限");
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(getPackageName(), "开启权限permission granted!");
                    //做下面该做的事
                    startScan();
                } else {
                    Toast.makeText(this, "没有定位权限，请先开启!", Toast.LENGTH_SHORT).show();
                    Log.e(getPackageName(), "没有定位权限，请先开启!");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 检测蓝牙是否打开或可用
    public boolean check() {
        return (null != bleAdapter && bleAdapter.isEnabled() && !bleAdapter.isDiscovering());
    }

    /**
     * 开始扫描
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startScan() {
        // 蓝牙是否可用
        if (!check()) {
            return;
        }
        strList.clear();
        adapter.notifyDataSetChanged();
        scanner.startScan(leCallback);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTargetDevice = devices.get(position);
                connect(mTargetDevice);
            }
        });
    }

    /**
     * 连接蓝牙设备
     *
     * @param device
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void connect(BluetoothDevice device) {
        if (!check()) {
            return;
        }
        // 停止扫描
        scanner.stopScan(leCallback);
        // 已经连接了其他设备
        if (mBluetoothGatt != null && mBluetoothGatt.connect()) {
            // 如果是先前连接的设备，则不做处理
            if (TextUtils.equals(device.getAddress(), mBluetoothGatt.getDevice().getAddress())) {
                return;
            }
            // 否则断开连接
            mBluetoothGatt.disconnect();
        }
        mBluetoothGatt = device.connectGatt(this, false, gattCallback);
        mBluetoothGatt.connect();
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (mBluetoothGatt != null && mBluetoothGatt.connect()) {
            mBluetoothGatt.disconnect();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnState.setText("断开连接");
            }
        });
    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }
}
