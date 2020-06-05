package com.sz.jjj.ipc.bindpond;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.sz.jjj.aidl.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * @author:jjj
 * @data:2018/7/11
 * @description:
 */

public class BinderPool {

    public static final String TAG = "BinderPool";
    public static final int BINDED_NONE = -1;
    public static final int BINDED_SECURITY_CENTER = 1;
    public static final int BINDED_COMPUTE = 2;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstence;
    private CountDownLatch mCountDownLatch;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstence(Context context) {
        if (sInstence == null) {
            synchronized (BinderPool.class) {
                if (sInstence == null) {
                    sInstence = new BinderPool(context);
                }
            }
        }
        return sInstence;
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    private synchronized void connectBinderPoolService() {
        mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(iBinder);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDED_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDED_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                default:
            }
            return binder;
        }
    }

}
