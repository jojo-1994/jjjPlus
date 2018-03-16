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
import com.sz.jjj.ble.utils.CRC16;

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
    private BluetoothGatt bleGatt;
    private BluetoothLeScanner scanner;
    private BluetoothDevice mTargetDevice;//从扫描到的设备列表里选出目标设备。

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
    @OnClick({R.id.btn_open_bluetooth, R.id.btn_search, R.id.btn_disconnect, R.id.btn_read})
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
            case R.id.btn_read:
                // 读取操作
//                bleGatt.readCharacteristic(characteristic);
                break;
        }
    }

    // 6.0 权限请求
    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
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
                    Log.e(getPackageName(), "没有定位权限，请先开启!");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 实现扫描回调接口
    private ScanCallback leCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BluetoothDevice device = result.getDevice();
                if (!devices.contains(device)) {  //判断是否已经添加
                    devices.add(device);
                    strList.add(device.getName() + "-----" + device.getAddress() + (device.getBondState() == BluetoothDevice.BOND_BONDED ? "已绑定" : "未绑定"));
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

    // 检测蓝牙是否打开或可用
    public boolean check() {
        return (null != bleAdapter && bleAdapter.isEnabled() && !bleAdapter.isDiscovering());
    }

    // 开始扫描
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startScan() {
        if (!check()) {
            return;  // 检测蓝牙
        }
        strList.clear();
        scanner.startScan(leCallback);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTargetDevice = devices.get(position);
                connect(mTargetDevice);
            }
        });
    }

    // 连接蓝牙设备，device为之前扫描得到的
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void connect(BluetoothDevice device) {
        if (!check()) {
            return;  // 检测蓝牙
        }
        if (null != bleAdapter) {
            bleAdapter.cancelDiscovery();
        }
        if (bleGatt != null && bleGatt.connect()) {  // 已经连接了其他设备
            // 如果是先前连接的设备，则不做处理
            if (TextUtils.equals(device.getAddress(), bleGatt.getDevice().getAddress())) {
                return;
            } else {
                bleGatt.disconnect(); // 否则断开连接
            }
        }

        bleGatt = device.connectGatt(this, false, gattCallback);
        bleGatt.connect();
    }

    public void disconnect() {
        if (null != bleGatt && bleGatt.connect()) {
            bleGatt.disconnect();
        }
        Log.e(TAG, "disconnect");
    }

    BluetoothGattCallback gattCallback = new BluetoothGattCallback() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d(TAG, "onConnectionStateChange: thread " + Thread.currentThread() + " status " + newState);
            if (status != BluetoothGatt.GATT_SUCCESS) {
                // 当尝试连接失败的时候调用 disconnect 方法是不会引起这个方法回调的，所以这里直接回调就可以了。
                String err = "Cannot connect device with error status: " + status;
                gatt.close();
                Log.e(TAG, err);
                return;
            }
            switch (newState) {//newState顾名思义，表示当前最新状态。status可以获取之前的状态。
                case BluetoothProfile.STATE_CONNECTED:
                    //这里表示已经成功连接，如果成功连接，我们就会执行discoverServices()方法去发现设备所包含的服务
                    onStateConnected(gatt);
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    //表示gatt连接已经断开。
                    onStateDisconnected(gatt);
                    break;
            }

        }

        //当我们执行了gatt.setCharacteristicNotification或写入特征的时候，结果会回调在此
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.e(TAG, gatt.getDevice().getName() + " write successfully");
        }

        //当我们执行了readCharacteristic()方法后，结果会回调在此。
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            String value = byteArrayToStr(characteristic.getValue());
            Log.e(TAG, gatt.getDevice().getName() + " recieved " + value);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //如果程序执行到这里，证明特征的读取已经完成，我们可以在回调当中取出特征的值。
                //特征所包含的值包含在一个byte数组内，我们可以定义一个临时变量来获取。

                byte[] characteristicValueBytes = characteristic.getValue();
                //如果这个特征返回的是一串字符串，那么可以直接获得其值
                String bytesToString = new String(characteristicValueBytes);
                Log.e("c-u", "" + Integer.parseInt(byteArrayToStr(characteristic.getValue()), 16) + bytesToString);
            }

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            String response = byteArrayToStr(characteristic.getValue());
            Log.e(TAG, "The response is " + response);
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {  // 成功订阅
                strList.clear();
                //gatt.getServices()可以获得外设的所有服务。
                List<BluetoothGattService> services = gatt.getServices();  // 获得设备所有的服务
                displayGattServices(services);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String BLE_Service = "11223344-5566-7788-99aa-bbccddeeff00";
                        String BLE_READ_WRITE = "00004a5b-0000-1000-8000-00805f9b34fb";
                        final UUID UUID_SERVICE = UUID.fromString(BLE_Service);
                        final UUID UUID_READ_WRITE = UUID.fromString(BLE_READ_WRITE);

                        BluetoothGattService mnotyGattService = bleGatt.getService(UUID_SERVICE);//找特定的某个服务
                        BluetoothGattCharacteristic mCharacteristic = mnotyGattService.getCharacteristic(UUID_READ_WRITE);

                        byte[] bytes =("&DC " + "18 02 07 13 06" + " ").getBytes();
                        byte[] test=getData(bytes);
                        mCharacteristic.setValue(test);//参数可以是byte数组，字符串等。
                        bleGatt.writeCharacteristic(mCharacteristic);
//                        bleGatt.setCharacteristicNotification(mCharacteristic, true);

//                    bleGatt.readCharacteristic(characteristics.get(position));
                    }
                });

            }
        }
    };

    private void onStateDisconnected(BluetoothGatt gatt) {
        gatt.close();
        Log.e(TAG, "断开连接");
        btnState.setText("断开连接");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void onStateConnected(BluetoothGatt gatt) {
        scanner.stopScan(leCallback);
        gatt.discoverServices(); // 连接成功，开始搜索服务，一定要调用此方法，否则获取不到服务
        btnState.setText("连接成功");
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) {
            return;
        }
        final List<BluetoothGattCharacteristic> characteristics = new ArrayList<>();
        List<BluetoothGattDescriptor> descriptors = new ArrayList<>();

        for (BluetoothGattService service : gattServices) {
            Log.e(TAG, "-- service uuid : " + service.getUuid().toString() + " --");
            strList.add(service.getUuid().toString());
            adapter.notifyDataSetChanged();
            // 每发现一个服务，我们再次遍历服务当中所包含的特征，service.getCharacteristics()可以获得当前服务所包含的所有特征
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                Log.e(TAG, "characteristic uuid : " + characteristic.getUuid());
                characteristics.add(characteristic);

                // String READ_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
                // 判断当前的Characteristic是否想要订阅的，这个要依据各自蓝牙模块的协议而定
                if (characteristic.getUuid().toString().equals("")) {
                    // 依据协议订阅相关信息,否则接收不到数据
                    bleGatt.setCharacteristicNotification(characteristic, true);

                    // 大坑，适配某些手机！！！比如小米...
                    bleGatt.readCharacteristic(characteristic);
                    // 遍历每个特征的描述符
                    for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                        bleGatt.writeDescriptor(descriptor);
                    }
                }

                for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                    Log.e(TAG, "descriptor uuid : " + characteristic.getUuid());
                    descriptors.add(descriptor);
                }
            }
        }
    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }


    /**
     * 获取包括整个CRC的数据
     */
    public static byte[] getData(byte[] b) {
        byte[] ch = getCRC16(b);
        byte[] bytes = new byte[b.length + ch.length + 2];
        for (int i = 0; i < b.length; i++) {
            bytes[i] = b[i];
        }
        bytes[b.length] = (byte) 0x06;
        for (int i = b.length + 1; i < bytes.length - 1; i++) {
            bytes[i] = ch[i - (b.length + 1)];
        }
        bytes[bytes.length - 1] = (byte) 0x0D;
        return bytes;
    }

    /**
     * 获取CRC字节码
     */
    public static byte[] getCRC16(byte[] b) {
        String s = CRC16.calcCrc16(b) + "";
        return s.getBytes();
    }

}
