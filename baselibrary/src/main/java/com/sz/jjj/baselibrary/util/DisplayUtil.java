package com.sz.jjj.baselibrary.util;

import android.content.Context;

/**
 * Created by jjj on 2017/3/23.
 *
 * @description:
 */

public class DisplayUtil {
    /**
     * 将px装换成dp，保证尺寸不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;//得到设备的密度
        return (int) (pxValue / density + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;//缩放密度
        return (int) (pxValue / scaleDensity + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaleDensity + 0.5f);
    }
}
