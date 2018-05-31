package com.sz.jjj.rxjavaretrofitdemo;

import android.app.Application;

import com.sz.jjj.baselibrary.config.TxConfiguration;
import com.sz.jjj.baselibrary.util.TxUtils;


/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        TxConfiguration configuration = new TxConfiguration.Builder(this)
                .baseUrl("https://api.douban.com/v2/movie/")
                .commonParam("token", "000")
                .postParam("post", "111")
                .headerParam("header", "222")
                .headerLine("333:444")
                .loginClass(MainActivity.class)
                .build();
        TxUtils.getInstance().init(configuration);
    }
}
