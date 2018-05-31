package com.sz.jjj.baselibrary.network.manager;

import android.os.Build;

import com.sz.jjj.baselibrary.config.TxConstants;
import com.sz.jjj.baselibrary.network.interceptor.BasicParamsInterceptor;
import com.sz.jjj.baselibrary.network.interceptor.LoggingInterceptor;
import com.sz.jjj.baselibrary.network.interceptor.TokenInterceptor;
import com.sz.jjj.baselibrary.util.LogUtil;
import com.sz.jjj.baselibrary.util.TxUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jjj
 * @date 2018/5/21
 * @description:
 */

public class OkHttpManager {
    /**
     * 单利引用
     */
    private static volatile OkHttpManager mInstance;

    /**
     * okHttpClient 实例
     */
    private OkHttpClient.Builder mBuilder;

    /**
     * 初始化OkHttpClient
     */
    public OkHttpManager() {
        BasicParamsInterceptor paramsInterceptor = new BasicParamsInterceptor.Builder()
                .addCommonParamsMap(TxUtils.getInstance().getConfiguration().getCommonParamsMap())
                .addPostParamsMap(TxUtils.getInstance().getConfiguration().getPostParamsMap())
                .addHeaderParamsMap(TxUtils.getInstance().getConfiguration().getHeaderParamsMap())
                .addHeaderLinesList(TxUtils.getInstance().getConfiguration().getHeaderLinesList())
                .build();

        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor("", true))
                .addInterceptor(paramsInterceptor)
                .addInterceptor(new TokenInterceptor());
//                .cache(new Cache(new File(context.getExternalCacheDir(), "okhttpcache"), 10 * 1024 * 1024)); // 10M缓存
    }

    /**
     * 获取单例引用
     *
     * @return OkHttpManager
     */
    public static OkHttpManager getInstance() {
        OkHttpManager inst = mInstance;
        if (inst == null) {
            synchronized (OkHttpManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new OkHttpManager();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public OkHttpClient.Builder getBuilder() {
        return mBuilder;
    }

    /**
     * okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public String requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s?%s", TxUtils.getInstance().getConfiguration().getBaseUrl(), actionUrl, tempParams.toString());
            LogUtil.e("ddddddd" + requestUrl);
            //创建一个请求
            Request request = new Request.Builder().url(requestUrl).build();
            //创建一个Call
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
            final Call call = mBuilder.build().newCall(request);
            //执行请求
            final Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return "";
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }
}
