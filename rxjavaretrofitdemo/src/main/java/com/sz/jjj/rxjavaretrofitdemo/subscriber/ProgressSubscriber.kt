package com.sz.jjj.rxjavaretrofitdemo.subscriber

import android.util.Log
import rx.Subscriber

/**
 * Created by jjj on 2017/7/13.
@description:
 */
open class ProgressSubscriber<T>(subscriber: SubscriberOnNextListener<T>) : Subscriber<T>() {

    val mSubscriber = subscriber

    override fun onStart() {

    }

    override fun onNext(t: T) {
        mSubscriber.onNext(t)
    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable?) {
        Log.e("call onError", e!!.message)
    }
}
