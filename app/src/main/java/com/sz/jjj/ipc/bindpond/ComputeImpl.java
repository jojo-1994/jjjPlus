package com.sz.jjj.ipc.bindpond;

import android.os.RemoteException;

import com.sz.jjj.aidl.ICompute;

/**
 * @author:jjj
 * @data:2018/7/11
 * @description:
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
