package com.sz.jjj.rxjavaretrofitdemo.subscriber

/**
 * Created by jjj on 2017/7/13.
@description: 请求完毕接口
 */
interface SubscriberOnNextListener<T> {
     fun onNext(t: T)
     fun onError(e: Throwable)
}
