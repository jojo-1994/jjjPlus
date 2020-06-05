package com.sz.jjj.baselibrary.util;

import android.content.Context;

import com.sz.jjj.baselibrary.config.TxConfiguration;
import com.sz.jjj.baselibrary.network.manager.OkHttpManager;

import okhttp3.OkHttpClient;


/**
 * @author jjj
 * @date 2018/5/21
 * @description:
 */

public class TxUtils {
    public static final String TAG = TxUtils.class.getSimpleName();

    private TxConfiguration mConfiguration;

    private OkHttpClient mOkHttpClient;

    private Context mContext;

    private volatile static TxUtils mInstance;

    public TxUtils() {
    }

    public static TxUtils getInstance() {
        if (mInstance == null) {
            synchronized (TxUtils.class) {
                if (mInstance == null) {
                    mInstance = new TxUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化配置
     *
     * @param configuration
     */
    public synchronized void init(TxConfiguration configuration) {
        this.mConfiguration = configuration;
        if (configuration.okhttpBuilder == null) {
            configuration.okhttpBuilder = OkHttpManager.getInstance().getBuilder();
        }
        this.mOkHttpClient = configuration.okhttpBuilder.build();
        this.mContext = configuration.context;
    }


    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Context getContext() {
        return mContext;
    }

    public TxConfiguration getConfiguration() {
        return mConfiguration;
    }

}
