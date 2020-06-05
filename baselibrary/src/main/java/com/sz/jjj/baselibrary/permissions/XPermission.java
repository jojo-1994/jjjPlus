package com.sz.jjj.baselibrary.permissions;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenY on 2017/1/17
 * 6.0权限请求页面.
 * 说明：正常使用时 内部进行了 6.0判断，在接口上 返回 true （以获取权限）
 */

public class XPermission {

    static final String TAG = "RxPermissions";


    public static void getPermissions(Activity activity, String[] permissions, boolean isShowDialog, boolean dialogCanCancel, OnPermissionsListener listener) {
        //如果低于版本6.0 ，则退出权限获取，返回 true
        if (Build.VERSION.SDK_INT < 23) {
            if (listener != null)
                listener.result(new String[0]);
            return;
        }

        //去除已以获取的权限
        String[] newPermissions = lacksPermissions(activity, permissions);
        if (newPermissions.length > 0) {
            fragmentPermisssions(activity, newPermissions, listener, isShowDialog, dialogCanCancel);
        } else {
            listener.result(new String[0]);
        }
    }

    public static void getPermissions(Activity activity, String[] permissions, OnPermissionsListener listener) {
        getPermissions(activity, permissions, false, false, listener);
    }

    /**
     * 通过Fragment 获取 权限
     *
     * @param activity
     * @param PERMISSIONS
     * @param listener
     * @param isShowDialog
     * @param dialogCanCancel
     */
    private static void fragmentPermisssions(Activity activity, String[] PERMISSIONS, OnPermissionsListener listener
            , boolean isShowDialog, boolean dialogCanCancel) {
        PermissionsFragment rxPermissionsFragment = (PermissionsFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (rxPermissionsFragment == null) {
            rxPermissionsFragment = new PermissionsFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(rxPermissionsFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        //设置监听
        rxPermissionsFragment.setListener(listener);
        //设置在没有成功获取权限时 是否弹框提示
        rxPermissionsFragment.setDialog(isShowDialog, dialogCanCancel);

        //去除已以获取的权限
        rxPermissionsFragment.requestPermissions(PERMISSIONS);
    }

    /**
     * @return 得到 未获取的权限集合
     */
    public static String[] lacksPermissions(Context context, String[] permissions) {
        List<String> list = new ArrayList<>();

        for (int i = 0; permissions != null && i < permissions.length; i++) {
            if (lacksPermission(context, permissions[i])) {
                list.add(permissions[i]);
            }
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        return newPermissions;
    }

    /**
     * 判断是否缺少权限(单个)
     *
     * @param permission
     * @return
     */
    private static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
