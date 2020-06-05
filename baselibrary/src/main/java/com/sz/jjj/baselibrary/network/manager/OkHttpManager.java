package com.sz.jjj.baselibrary.network.manager;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.sz.jjj.baselibrary.config.TxConstants;
import com.sz.jjj.baselibrary.network.interceptor.BasicParamsInterceptor;
import com.sz.jjj.baselibrary.network.interceptor.LoggingInterceptor;
import com.sz.jjj.baselibrary.network.interceptor.TokenInterceptor;
import com.sz.jjj.baselibrary.network.progress.IProgressListener;
import com.sz.jjj.baselibrary.network.progress.ProgressResponseBody;
import com.sz.jjj.baselibrary.util.LogUtil;
import com.sz.jjj.baselibrary.util.TxUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
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
        //处理参数
        String requestUrl = getRequestUrl(actionUrl, paramsMap);
        LogUtil.e("ddddddd" + requestUrl);
        //创建一个请求
        Request request = new Request.Builder().url(requestUrl).build();
        //创建一个Call
        final Call call = mBuilder.build().newCall(request);
        //执行请求
        final Response response;
        try {
            response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void download(String actionUrl, HashMap<String, String> paramsMap, final IProgressListener progressListener) {
//        String requestUrl = getRequestUrl(actionUrl, paramsMap);
        String requestUrl = "http://oh0vbg8a6.bkt.clouddn.com/app-debug.apk";
        LogUtil.e("ddddddd" + requestUrl);
        //创建一个请求
        Request request = new Request.Builder().url(requestUrl).tag(requestUrl).build();
        //创建一个Call

//        mBuilder.addInterceptor(new ProgressInterceptor(progressListener));
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        };
        mBuilder.addInterceptor(interceptor);
        final Call call = mBuilder.build().newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "下载错误： " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                long sum = 0L;
                //文件总大小
                final long total = response.body().contentLength();
                int len = 0;
                File file  = new File(Environment.getExternalStorageDirectory(), "n.png");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1){
                    fos.write(buf, 0, len);
                    //每次递增
                    sum += len;

                    final long finalSum = sum;
                    Log.d("pyh1", "onResponse: " + finalSum + "/" + total);

                }
                fos.flush();
                fos.close();
                is.close();


            }
        });

    }

    private String getRequestUrl(String actionUrl, HashMap<String, String> paramsMap) {
        String baseUrl = TxUtils.getInstance().getConfiguration().getBaseUrl();
        // actionUrl是完整http
        if (actionUrl.contains("http")) {
            baseUrl = "";
        }
        // 参数为空
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        StringBuilder tempParams = new StringBuilder();
        //处理参数
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            //对参数进行URLEncoder
            try {
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos++;
        }
        //补全请求地址
        String requestUrl = String.format("%s/%s?%s", baseUrl, actionUrl, tempParams.toString());
        return requestUrl;
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
