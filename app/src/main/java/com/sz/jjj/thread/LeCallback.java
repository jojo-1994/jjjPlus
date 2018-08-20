package com.sz.jjj.thread;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import java.lang.ref.WeakReference;

/**
 * @author:jjj
 * @data:2018/8/14
 * @description:
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LeCallback extends ScanCallback {

    private final WeakReference<LeCallbackInterface> mLeCallbackInterface;

    public LeCallback(LeCallbackInterface leCallbackInterface) {
        this.mLeCallbackInterface = new WeakReference<LeCallbackInterface>(leCallbackInterface);
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        if(mLeCallbackInterface.get() != null){
            mLeCallbackInterface.get().onScanResult(result);
        }
    }

    @Override
    public void onScanFailed(int errorCode) {
        super.onScanFailed(errorCode);
    }

    public interface LeCallbackInterface {
        void onScanResult(ScanResult result);
    }

}
