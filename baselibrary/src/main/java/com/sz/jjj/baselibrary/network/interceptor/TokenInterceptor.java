package com.sz.jjj.baselibrary.network.interceptor;

import com.sz.jjj.baselibrary.network.manager.OkHttpManager;
import com.sz.jjj.baselibrary.util.LogUtil;
import com.sz.jjj.baselibrary.util.TxUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:jjj
 * @data:2018/5/23
 * @description: token拦截器
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        HttpUrl.Builder builder = httpUrl.newBuilder();
        // 移除公共参数
        Map<String, String> commonMap = TxUtils.getInstance().getConfiguration().getCommonParamsMap();
        for (String name : httpUrl.queryParameterNames()) {
            List<String> values = httpUrl.queryParameterValues(name);
            if (commonMap.containsKey(name) && values.size() > 1 && values.get(values.size() - 1).contains("")) {
                builder.removeAllQueryParameters(name);
            }
        }
        // 判断token是否过期
        String token = httpUrl.queryParameter("token");
        if (token != null && token.equals("过期")) {
            // TODO:同步请求获取新的token
            HashMap<String, String> map = new HashMap<>();
            map.put("start", "0");
            map.put("count", "2");
            map.put("token", ""); // 移除token
            OkHttpManager.getInstance().requestGetBySyn("top250", map);
            LogUtil.e("ddddddd666" + "重新请求");
            String newToken = "111";
            builder.setEncodedQueryParameter("token", newToken);
            builder.setEncodedQueryParameter("count", "3");
            return chain.proceed(getNewRequest(request, builder));
        }
        return chain.proceed(getNewRequest(request, builder));
    }

    private Request getNewRequest(Request request, HttpUrl.Builder builder) {
        return request.newBuilder().url(builder.build()).build();
    }
}
