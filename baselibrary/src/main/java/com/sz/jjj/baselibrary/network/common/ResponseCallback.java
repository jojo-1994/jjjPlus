package com.sz.jjj.baselibrary.network.common;

import com.sz.jjj.baselibrary.network.exception.TxExecption;

/**
 * @author:jjj
 * @data:2018/5/23
 * @description:
 */

public interface ResponseCallback<T> {
    /**
     * 数据返回成功
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     * 数据返回失败
     *
     * @param e
     */
    void onFailure(TxExecption e);

    /**
     * 请求完成
     */
    void onComplete();
}
