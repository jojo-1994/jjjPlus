package com.sz.jjj.baselibrary.network.manager;

import com.sz.jjj.baselibrary.util.TxUtils;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */
public class RetrofitManager {

    private static HashMap<String, Object> mServiceMap = new HashMap<String, Object>();

    /**
     * 创建Retrofit Service
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> t) {
        return create(t, TxUtils.getInstance().getConfiguration().getBaseUrl());
    }

    /**
     * 创建Retrofit Service，自定义接口地址url
     *
     * @param t
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> t, String url) {
        T service = (T) mServiceMap.get(t.getName());
        if (service == null) {
            Retrofit retrofit = getRetrofit(url);
            service = retrofit.create(t);
            mServiceMap.put(t.getName(), service);
        }
        return service;
    }

    private static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .client(TxUtils.getInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }
}
