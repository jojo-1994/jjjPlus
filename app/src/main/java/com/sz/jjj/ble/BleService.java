package com.sz.jjj.ble;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * Created by jjj on 2017/11/29.
 *
 * @description:
 */

public class BleService extends Service {

    private BleBinder mBleBinder;
    private Handler mBleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBleBinder;
    }

    public class BleBinder extends Binder {
        //在此提供对外部的调用方法，当某活动绑定此服务后，获得返回mBleBinder对象，外部活动通过操作mBleBinder的方法来控制蓝牙设备。
        public void startScan() {
            //开始扫描......
        }

        public void stopScan() {
            //停止扫描........
        }
        //更多方法........需要注意的是，某些方法为耗时操作，有必要时应该开启子线程去执行。
        //而且蓝牙很多时候都是异步操作，需要使用许多回调方法。
        //如果此服务为独立进程服务，并为其他app提供数据，需要注意方法同步。
    }
}

