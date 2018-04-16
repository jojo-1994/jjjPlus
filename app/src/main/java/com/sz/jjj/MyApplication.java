package com.sz.jjj;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.WindowManager;

import com.sz.jjj.dagger.MyScope;

import javax.inject.Inject;

import dagger.releasablereferences.ForReleasableReferences;
import dagger.releasablereferences.ReleasableReferenceManager;


/**
 * Created by jjj on 2017/7/28.
 *
 * @description:
 */

public class MyApplication extends Application {

    public final static float DESIGN_WIDTH = 750; //绘制页面时参照的设计图宽度

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Application", "onCreate: process name -->" + getProcessName());
        Log.e("Application", "onCreate: package name -->" + getPackageName());
        String processNameString = getProcessName();
        if (getPackageName().equals(processNameString)) {
            resetDensity();
        }
    }


    /**
     * @return 获取进程名
     */
    public String getProcessName() {
        int pid = android.os.Process.myPid();//获取进程pid
        String processName = "";
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);//获取系统的ActivityManager服务
        for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        return processName;
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

    @Inject
    @ForReleasableReferences(MyScope.class)
    ReleasableReferenceManager myScopeReferences;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        myScopeReferences.releaseStrongReferences();
    }

}
