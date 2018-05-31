package com.sz.jjj.baselibrary.config;


import android.content.Context;
import android.text.TextUtils;

import com.sz.jjj.baselibrary.imageloader.BaseImageLoaderProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;


/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */

public class TxConfiguration {

    public final Context context;
    final Class loginClass;

    private String baseUrl;
    final Map<String, String> comParamsMap;
    final Map<String, String> postParamsMap;
    final Map<String, String> headerParamsMap;
    final List<String> headerLinesList;

    final BaseImageLoaderProvider imageLoaderProvider;

    public OkHttpClient.Builder okhttpBuilder;
    final boolean isCacheOn;
    final boolean isCacheFromHeader;
    final int cacheTime;

    public TxConfiguration(final Builder builder) {
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.comParamsMap = builder.comParamsMap;
        this.postParamsMap = builder.postParamsMap;
        this.headerParamsMap = builder.headerParamsMap;
        this.headerLinesList = builder.headerLinesList;
        this.okhttpBuilder = builder.okhttpBuilder;
        this.loginClass = builder.loginClass;
        this.imageLoaderProvider = builder.imageLoaderProvider;
        this.isCacheOn = builder.isCacheOn;
        this.cacheTime = builder.cacheTime;
        this.isCacheFromHeader = builder.isCacheFromHeader;
    }


    public Context getContext() {
        return context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, String> getCommonParamsMap() {
        return comParamsMap;
    }

    public Map<String, String> getPostParamsMap() {
        return postParamsMap;
    }

    public Map<String, String> getHeaderParamsMap() {
        return headerParamsMap;
    }

    public List<String> getHeaderLinesList() {
        return headerLinesList;
    }

    public OkHttpClient.Builder getOkhttpBuilder() {
        return okhttpBuilder;
    }

    public Class getLoginClass() {
        return loginClass;
    }

    public boolean isCacheOn() {
        return isCacheOn;
    }

    public static class Builder {
        private String baseUrl;
        private Map<String, String> comParamsMap;
        private Map<String, String> postParamsMap;
        private Map<String, String> headerParamsMap;
        private List<String> headerLinesList;

        private BaseImageLoaderProvider imageLoaderProvider;

        private OkHttpClient.Builder okhttpBuilder;

        private Context context;
        private Class loginClass;

        private boolean isCacheOn = false;
        private boolean isCacheFromHeader = true;
        private int cacheTime = 0;

        public Builder(Context context) {
            this.context = context;
            comParamsMap = new HashMap<>();
            postParamsMap = new HashMap<>();
            headerParamsMap = new HashMap<>();
            headerLinesList = new ArrayList<>();
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder commonParamMap(Map<String, String> params) {
            this.comParamsMap.putAll(params);
            return this;
        }

        public Builder commonParam(String key, String value){
            this.comParamsMap.put(key, value);
            return this;
        }

        public Builder postParamMap(Map<String, String> params) {
            this.postParamsMap.putAll(params);
            return this;
        }

        public Builder postParam(String key, String value){
            this.postParamsMap.put(key, value);
            return this;
        }

        public Builder headerParamsMap(Map<String, String> params) {
            this.headerParamsMap.putAll(params);
            return this;
        }

        public Builder headerParam(String key, String value){
            this.headerParamsMap.put(key, value);
            return this;
        }

        public Builder headerLinesList(List<String> params) {
            this.headerLinesList.addAll(params);
            return this;
        }

        public Builder headerLine(String value){
            this.headerLinesList.add(value);
            return this;
        }

        public Builder imageLoader(BaseImageLoaderProvider imageLoaderProvider) {
            this.imageLoaderProvider = imageLoaderProvider;
            return this;
        }

        public Builder okhttpBuilder(OkHttpClient.Builder okhttpBuilder) {
            this.okhttpBuilder = okhttpBuilder;
            return this;
        }

        public Builder isCacheOn(boolean isCacheOn) {
            this.isCacheOn = isCacheOn;
            return this;
        }

        public Builder isCacheFromHeader(boolean isCacheFromHeader) {
            this.isCacheFromHeader = isCacheFromHeader;
            return this;
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder loginClass(Class loginClass) {
            this.loginClass = loginClass;
            return this;
        }

        public TxConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new TxConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = TxConstants.BASE_URL;
            }
        }

    }

}
