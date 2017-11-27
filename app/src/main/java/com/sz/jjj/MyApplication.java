package com.sz.jjj;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;


/**
 * Created by jjj on 2017/7/28.
 *
 * @description:
 */

public class MyApplication extends Application {

    public final static float DESIGN_WIDTH = 750; //绘制页面时参照的设计图宽度

    @Override
    public void onCreate() {
        super.onCreate();
        resetDensity();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }

    public void resetDensity() {
        Point size = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }


}
