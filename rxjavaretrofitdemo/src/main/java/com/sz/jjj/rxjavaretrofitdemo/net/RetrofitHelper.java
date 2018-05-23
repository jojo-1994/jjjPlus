package com.sz.jjj.rxjavaretrofitdemo.net;

import com.sz.jjj.baselibrary.network.manager.RetrofitManager;

/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */

public class RetrofitHelper {

    private static ApiService sApiService;

    public static ApiService getApiService() {
        return sApiService;
    }

    static {
        sApiService = RetrofitManager.create(ApiService.class);
    }
}
