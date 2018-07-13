package com.sz.jjj.ipc.bindpond;

import android.os.RemoteException;

import com.sz.jjj.aidl.ISecurityCenter;

/**
 * @author:jjj
 * @data:2018/7/11
 * @description:
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    public static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
