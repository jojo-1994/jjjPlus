package com.sz.jjj.baselibrary.network.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.sz.jjj.baselibrary.util.LogUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 *
 * @author jjj
 * @date 2018/5/17
 *
 * @description: 日志打印拦截器
 */

public class LoggingInterceptor implements Interceptor {

    public static final String TAG = "TxUtils";
    private static final String KEY_JSON = "json";
    private String tag;
    private boolean showLog;

    public LoggingInterceptor(String tag, boolean showLog) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showLog = showLog;
        this.tag = tag;
    }

    public LoggingInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //请求发起的时间
        long t1 = System.currentTimeMillis();
        if (showLog) {
            logForRequest(request);
        }
        Response response = chain.proceed(request);
        //收到响应的时间
        long t2 = System.currentTimeMillis();
        if (showLog) {
            Log.e(tag, "请求用时 : " + (t2 - t1));
            return logForResponse(response);
        }
        ResponseBody body = response.body();
        return response.newBuilder().body(body).build();
    }

    private Response logForResponse(Response response) throws IOException {
        //===>response log
        Log.e(tag, "========response'log=======");
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        Log.e(tag, "mUrl : " + clone.request().url());
        Log.e(tag, "code : " + clone.code());
        Log.e(tag, "protocol : " + clone.protocol());
        if (!TextUtils.isEmpty(clone.message())) {
            Log.e(tag, "message : " + clone.message());
        }

        if (showLog) {
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();

                        if (!clone.isSuccessful()) {
                            LogUtil.e("responseBody's content : " + resp);
                        } else {
                            if (resp.contains("errcode")) {
                                Log.e(tag, "responseBody's content : " + resp);
                            } else {
                                Log.e(tag, "responseBody's content : " + resp);
                            }
                        }

                        body = ResponseBody.create(mediaType, resp);

                        Log.e(tag, "========response'log=======end");
                        return response.newBuilder().body(body).build();
                    } else {
                        Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        }

        Log.e(tag, "========response'log=======end");

        return response;
    }

    private void logForRequest(Request request) {
        try {
            Headers headers = request.headers();

            Log.e(tag, "========request'log=======");
            Log.e(tag, "method : " + request.method());
            String url = request.url().queryParameter(KEY_JSON);
            Log.e(tag, "mUrl : " + request.url().toString());
            Log.e(tag, "json : " + "json=" + url);
            Log.e(tag, "tag : " + request.tag());
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e) {
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    ) {
                return true;
            }
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
