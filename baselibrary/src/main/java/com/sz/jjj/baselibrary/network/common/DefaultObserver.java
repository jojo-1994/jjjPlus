package com.sz.jjj.baselibrary.network.common;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.sz.jjj.baselibrary.R;
import com.sz.jjj.baselibrary.network.exception.NoDataExceptionException;
import com.sz.jjj.baselibrary.network.exception.ServerResponseException;
import com.sz.jjj.baselibrary.network.exception.TxExecption;
import com.sz.jjj.baselibrary.util.ToastUtil;
import com.sz.jjj.baselibrary.util.TxUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */

public abstract class DefaultObserver<T> implements Observer<T>, ResponseCallback<T> {

    private boolean showToast;

    public DefaultObserver() {
        this(true);
    }

    public DefaultObserver(boolean showToast) {
        this.showToast = showToast;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(final T response) {
        onSuccess(response);
        onComplete();
    }

    @Override
    public void onError(final Throwable e) {
        Context context = TxUtils.getInstance().getContext();
        String errMsg = "";
        //   HTTP错误
        if (e instanceof HttpException) {
            errMsg = context.getString(R.string.bad_network);
        } //   连接错误
        else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            errMsg = context.getString(R.string.connect_error);
        } //  连接超时
        else if (e instanceof InterruptedIOException) {
            errMsg = context.getString(R.string.connect_timeout);
        }  //  解析错误
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            errMsg = context.getString(R.string.parse_error);
        } //  服务器响应失败
        else if (e instanceof ServerResponseException) {
            errMsg = context.getString(R.string.response_failure);
        } //  服务器返回异常
        else if (e instanceof NoDataExceptionException) {
            errMsg = context.getString(R.string.response_data_error);
        } //  未知错误
        else {
            errMsg = context.getString(R.string.bad_network);
        }
        if (showToast) {
            ToastUtil.show(context, errMsg);
        }
        onFailure(new TxExecption(errMsg));
    }

    @Override
    public void onFailure(TxExecption e) {

    }

    @Override
    public void onComplete() {
    }

}

