package com.sz.jjj.baselibrary.network.dialog;

import android.app.Activity;
import android.graphics.Color;

import java.lang.ref.WeakReference;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author:jjj
 * @data:2018/5/22
 * @description:
 */

public class DialogUtils {
    /**
     * 加载进度的dialog
     */
    private SweetAlertDialog mDialog;


    /**
     * 显示ProgressDialog
     */
    public void showProgress(Activity context) {
        showProgress(context, "Loading");
    }

    public void showProgress(Activity activity, String msg) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        if (weakReference.get() != null && !weakReference.get().isFinishing()) {
            if (mDialog == null) {
                mDialog = new SweetAlertDialog(weakReference.get(), SweetAlertDialog.PROGRESS_TYPE);
                mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                mDialog.setTitleText(msg);
                mDialog.setCancelable(false);
            }
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        if (weakReference.get() != null && !weakReference.get().isFinishing()) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        }
    }
}
