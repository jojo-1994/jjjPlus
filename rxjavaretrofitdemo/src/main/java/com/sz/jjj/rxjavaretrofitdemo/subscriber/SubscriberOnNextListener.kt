package com.sz.jjj.rxjavaretrofitdemo.subscriber

/**
 * Created by jjj on 2017/7/13.
@description:
 */
interface SubscriberOnNextListener<T> {
     fun onNext(t: T)
}