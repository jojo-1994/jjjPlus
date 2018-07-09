package com.tianxiabuyi.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.taidoc.pclinklibrary.android.bluetooth.util.BluetoothUtil;
import com.taidoc.pclinklibrary.connection.AndroidBluetoothConnection;
import com.taidoc.pclinklibrary.connection.util.ConnectionManager;
import com.taidoc.pclinklibrary.constant.PCLinkLibraryConstant;
import com.taidoc.pclinklibrary.constant.PCLinkLibraryEnum;
import com.taidoc.pclinklibrary.exceptions.CommunicationTimeoutException;
import com.taidoc.pclinklibrary.exceptions.ExceedRetryTimesException;
import com.taidoc.pclinklibrary.exceptions.NotConnectSerialPortException;
import com.taidoc.pclinklibrary.exceptions.NotSupportMeterException;
import com.taidoc.pclinklibrary.meter.AbstractMeter;
import com.taidoc.pclinklibrary.meter.record.AbstractRecord;
import com.taidoc.pclinklibrary.meter.record.BloodGlucoseRecord;
import com.taidoc.pclinklibrary.meter.record.BloodPressureRecord;
import com.taidoc.pclinklibrary.meter.util.MeterManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:jjj
 * @data:2018/6/5
 * @description:
 */

public class ConnectPresenter {

    // Message types sent from the meterCommuHandler Handler
    public static final int MESSAGE_STATE_CONNECTING = 1;
    public static final int MESSAGE_STATE_CONNECT_FAIL = 2;
    public static final int MESSAGE_STATE_CONNECT_DONE = 3;
    public static final int MESSAGE_STATE_CONNECT_NONE = 4;
    public static final int MESSAGE_STATE_CONNECT_METER_SUCCESS = 5;
    public static final int MESSAGE_STATE_CHECK_METER_INFORMATION = 6;
    public static final int MESSAGE_STATE_CHECK_METER_BT_DISTENCE = 7;
    public static final int MESSAGE_STATE_CHECK_METER_BT_DISTENCE_FAIL = 8;
    public static final int MESSAGE_STATE_NOT_SUPPORT_METER = 9;
    public static final int MESSAGE_STATE_NOT_CONNECT_SERIAL_PORT = 10;
    public static final int MESSAGE_STATE_SCANED_DEVICE = 11;

    private Activity mActivity;
    private String mMacAddress;
    /**
     * Android BT connection
     */
    private AndroidBluetoothConnection mConnection;
    private static final String TAG = "dddddddd";

    private AbstractMeter mTaiDocMeter = null;

    public ConnectPresenter(Activity activity, String mMacAddress) {
        this.mActivity = activity;
        this.mMacAddress = mMacAddress;
        setupAndroidBluetoothConnection();
    }

    public void setupAndroidBluetoothConnection() {
        if (mConnection == null) {
            // 这里一定要用一个try-catch, 因为在4.3以前是无法用ble的,会造成runtime error
            try {
                mConnection = ConnectionManager.createAndroidBluetoothConnection(mBTConnectionHandler);
                mConnection.canScanV3KNV(false);
            } catch (Exception ee) {
            }
        } /* end of if */
    }

    /**
     * 蓝牙连接的Handler
     */
    private final Handler mBTConnectionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case PCLinkLibraryConstant.MESSAGE_STATE_CHANGE:
                        switch (msg.arg1) {
                            case AndroidBluetoothConnection.STATE_CONNECTED_BY_LISTEN_MODE:
                                Log.d(TAG, "获取蓝牙仪器");
                                try {
                                    mTaiDocMeter = MeterManager.detectConnectedMeter(mConnection);
                                } catch (Exception e) {
                                    throw new NotSupportMeterException();
                                }
                                if (mTaiDocMeter == null) {
                                    throw new NotSupportMeterException();
                                }
                                break;
                            case AndroidBluetoothConnection.STATE_CONNECTING:
                                // 暂无需特别处理的事项
                                break;
                            case AndroidBluetoothConnection.STATE_SCANED_DEVICE:
                                meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_SCANED_DEVICE);
                                break;
                            case AndroidBluetoothConnection.STATE_LISTEN:
                                // 暂无需特别处理的事项
                                break;
                            case AndroidBluetoothConnection.STATE_NONE:
                                // 暂无需特别处理的事项
                                break;
                        } /* end of switch */
                        break;
                    case PCLinkLibraryConstant.MESSAGE_TOAST:
                        // 暂无需特别处理的事项
                        break;
                    default:
                        break;
                } /* end of switch */
            } catch (NotSupportMeterException e) {
                Log.e(TAG, "not support meter", e);
            } /* end of try-catch */
        }
    };

    /**
     * 控制Meter连接时与UI互通的Handler
     */
    private final Handler meterCommuHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CONNECTING:
                    Log.e(TAG, "正在连接中");
                    break;
                case MESSAGE_STATE_SCANED_DEVICE:
                    Log.e(TAG, "扫描到蓝牙设备，开始连接");
                    // 取得Bluetooth Device資訊
                    final BluetoothDevice device = BluetoothUtil.getPairedDevice(mConnection.getConnectedDeviceAddress());
                    // Attempt to connect to the device
                    mConnection.LeConnect(mActivity.getApplicationContext(), device);
                    // 在mLeConnectedListener會收
                    break;
                case MESSAGE_STATE_CONNECT_DONE:
                    Log.e(TAG, "连接完成");
                    break;
                case MESSAGE_STATE_CONNECT_FAIL:
                    Log.e(TAG, "连接失败");
                    break;
                case MESSAGE_STATE_CONNECT_NONE:
                    Log.e(TAG, "没有相关连接");
                    //showAlertDialog(PCLinkLibraryCommuTestActivity.this, "00000");
                    break;
                case MESSAGE_STATE_CONNECT_METER_SUCCESS:
                    Log.e(TAG, "连接到蓝牙设备");
                    Toast.makeText(mActivity, "连接到蓝牙设备", Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_STATE_CHECK_METER_BT_DISTENCE:
                    Log.e(TAG, "MESSAGE_STATE_CHECK_METER_BT_DISTENCE");
                    break;
                case MESSAGE_STATE_CHECK_METER_BT_DISTENCE_FAIL:
                    Log.e(TAG, "MESSAGE_STATE_CHECK_METER_BT_DISTENCE_FAIL");
                    break;
                case MESSAGE_STATE_NOT_SUPPORT_METER:
                    Log.e(TAG, "MESSAGE_STATE_NOT_SUPPORT_METER");
                    break;
                case MESSAGE_STATE_NOT_CONNECT_SERIAL_PORT:
                    Log.e(TAG, "MESSAGE_STATE_NOT_SUPPORT_METER");
                    break;
            } /* end of switch */
        }
    };

    /**
     * Connect Meter
     */
    public void connectMeter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_CONNECTING);
                    updatePairedList();
                    mConnection.setLeConnectedListener(mLeConnectedListener);
                    Log.e(TAG, "connectionning");
                    if (mConnection.getState() == AndroidBluetoothConnection.STATE_NONE) {
                        // Start the Android Bluetooth connection services to listen mode
                        Log.e(TAG, "state none");
                        mConnection.LeListen();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mConnection.getState() == AndroidBluetoothConnection.STATE_LISTEN) {
                                if (mLeConnectedListener != null) {
                                    mLeConnectedListener.onConnectionTimeout();
                                    Log.e(TAG, "connection timeout");
                                }
                            }
                        }
                    }, 10000);

                } catch (CommunicationTimeoutException e) {
                    meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_CONNECT_FAIL);
                } catch (NotSupportMeterException e) {
                    meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_NOT_SUPPORT_METER);
                } catch (NotConnectSerialPortException e) {
                    meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_NOT_CONNECT_SERIAL_PORT);
                } catch (ExceedRetryTimesException e) {
                    meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_NOT_SUPPORT_METER);
                } finally {

                }
                Looper.loop();
            }
        }).start();
    }

    private void updatePairedList() {
        Map<String, String> addrs = new HashMap(2);
        String addrKey = "BLE_PAIRED_METER_ADDR_" + String.valueOf(0);
        String addrKey2 = "BLE_PAIRED_METER_ADDR_" + String.valueOf(1);
//        addrs.put(addrKey, mMacAddress);
        addrs.put(addrKey2, "C0:26:DA:00:2D:81");
        addrs.put(addrKey, "C0:26:DF:03:56:17");
        mConnection.updatePairedList(addrs, 2);
    }

    private AndroidBluetoothConnection.LeConnectedListener mLeConnectedListener = new AndroidBluetoothConnection.LeConnectedListener() {

        @Override
        public void onConnectionTimeout() {
            Log.e(TAG, "onConnectionTimeout");
        }

        @Override
        public void onConnectionStateChange_Disconnect(BluetoothGatt gatt,
                                                       int status, int newState) {
            Log.e(TAG, "disconnect");
            meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_CONNECT_NONE);
        }

        @SuppressLint("NewApi")
        @Override
        public void onDescriptorWrite_Complete(BluetoothGatt gatt,
                                               BluetoothGattDescriptor descriptor, int status) {
            mConnection.LeConnected(gatt.getDevice());
            Log.e(TAG, "complete");
        }

        @Override
        public void onCharacteristicChanged_Notify(BluetoothGatt gatt,
                                                   BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, "onCharacteristicChanged_Notify");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();

                    try {
                        mTaiDocMeter = MeterManager.detectConnectedMeter(mConnection);
                        meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_CONNECT_METER_SUCCESS);
                        Log.e(TAG, "meter connected");
                    } catch (Exception e) {
                        meterCommuHandler.sendEmptyMessage(MESSAGE_STATE_NOT_SUPPORT_METER);
                    }

                    Looper.loop();
                }
            }).start();
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onCharacteristicChanged");

        }
    };


    public void disconnectMeter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    if (mTaiDocMeter != null) {
                        mTaiDocMeter.turnOffMeterOrBluetooth(0);
                        mTaiDocMeter=null;
                    }
                    mConnection.disconnect();
                    mConnection.setLeConnectedListener(null);
                    mConnection.LeDisconnect();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                } finally {
                }/* end of try-catch-finally */
                Looper.loop();
            }
        }).start();
    }

    public void getLastData() {
        if (mTaiDocMeter != null) {
            AbstractRecord abstractRecord = mTaiDocMeter.getStorageDataRecord(0);
            if (abstractRecord instanceof BloodGlucoseRecord) {
                Log.e(TAG, ((BloodGlucoseRecord) abstractRecord).getGlucoseValue() + "---" + ((BloodGlucoseRecord) abstractRecord).getMeasureTime());
            } else if (abstractRecord instanceof BloodPressureRecord) {
                Log.e(TAG, ((BloodPressureRecord) abstractRecord).getMapValue() + "---" + ((BloodPressureRecord) abstractRecord).getMeasureTime());
            }
        }
    }

    public void getAllData() {
        if (mTaiDocMeter != null) {
            List<AbstractRecord> list = mTaiDocMeter.getAllStorageDataRecord(PCLinkLibraryEnum.User.CurrentUser);
            for (AbstractRecord abstractRecord : list) {
                if (abstractRecord instanceof BloodGlucoseRecord) {
                    Log.e(TAG, ((BloodGlucoseRecord) abstractRecord).getGlucoseValue() + "---" + ((BloodGlucoseRecord) abstractRecord).getMeasureTime());
                } else if (abstractRecord instanceof BloodPressureRecord) {
                    Log.e(TAG, ((BloodPressureRecord) abstractRecord).getMapValue() + "---" + ((BloodPressureRecord) abstractRecord).getMeasureTime());
                }
            }
            disconnectMeter();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    connectMeter();
                }
            }, 1000);
        }
    }
}
