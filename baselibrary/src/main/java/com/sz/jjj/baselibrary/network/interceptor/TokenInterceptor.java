package com.sz.jjj.baselibrary.network.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:jjj
 * @data:2018/5/23
 * @description:
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        HttpUrl.Builder builder = httpUrl.newBuilder();
        for (String str : httpUrl.encodedPathSegments()) {
            Log.e("dddddd111", str);
        }
        for (int i = 0; i < httpUrl.querySize(); i++) {
            Log.e("dddddd222", httpUrl.queryParameterName(i));
            Log.e("dddddd333", httpUrl.queryParameterValue(i));
        }
        String token = httpUrl.queryParameter("token");
        Log.e("dddddd444", token);
        Response response = chain.proceed(request);
        if (token.equals("000")) {
            builder.setEncodedQueryParameter("token", "111");
            Log.e("dddddd555", "token过期");
            return chain.proceed(request.newBuilder().url(builder.build()).build());
        }
        Log.e("dddddd666", "response");
        return response.newBuilder().body(response.body()).build();
    }
}
