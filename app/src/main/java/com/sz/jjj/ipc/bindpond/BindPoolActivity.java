package com.sz.jjj.ipc.bindpond;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.jjj.aidl.ICompute;
import com.sz.jjj.aidl.ISecurityCenter;

/**
 * @author:jjj
 * @data:2018/7/11
 * @description:
 */

public class BindPoolActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstence(this);
        IBinder iBinder = binderPool.queryBinder(BinderPool.BINDED_SECURITY_CENTER);
        ISecurityCenter iSecurityCenter = SecurityCenterImpl.asInterface(iBinder);
        Log.e(BinderPool.TAG, "");
        String msg = "helloworld-安卓";
        try {
            String password = iSecurityCenter.encrypt(msg);
            Log.e(BinderPool.TAG, "encrypt:" + password);
            Log.e(BinderPool.TAG, iSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(BinderPool.TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDED_COMPUTE);
        ICompute iCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.e(BinderPool.TAG, "3+5=" + iCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
