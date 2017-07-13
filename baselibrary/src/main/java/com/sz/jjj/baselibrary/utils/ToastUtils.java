package com.sz.jjj.baselibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * Created by xjh1994 on 2016/7/15.
 */
public class ToastUtils {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    private ToastUtils() {
            /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            show(context, message, Toast.LENGTH_LONG);
//            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, CharSequence message) {
        if (isShow)
            show(context, message, Toast.LENGTH_SHORT);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int message) {
        if (isShow)
            show(context, message, Toast.LENGTH_SHORT);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
//        if (isShow)
//            Toast.makeText(context, message, duration).show();
        if (TextUtils.isEmpty(message) || !isShow) {
            return;
        }
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(message);
            mToast.show();
        } else {
            mToast = Toast.makeText(context, message, duration);
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

//    public static void show(int message) {
//        show(TxUtils.getInstance().getContext(), message);
//    }
//
//
//    public static void show(CharSequence message) {
//        show(TxUtils.getInstance().getContext(), message);
//    }
//
//    public static void showLong(int message) {
//        showLong(TxUtils.getInstance().getContext(), message);
//    }
//
//    public static void showLong(CharSequence message) {
//        showLong(TxUtils.getInstance().getContext(), message);
//    }

}
