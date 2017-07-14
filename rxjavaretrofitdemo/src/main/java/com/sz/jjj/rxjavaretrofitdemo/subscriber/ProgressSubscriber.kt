package com.sz.jjj.rxjavaretrofitdemo.subscriber

import android.content.Context
import android.util.Log
import com.sz.jjj.baselibrary.utils.ToastUtils
import com.sz.jjj.rxjavaretrofitdemo.progress.ProgressCancelListener
import com.sz.jjj.rxjavaretrofitdemo.progress.ProgressDialogHandler
import rx.Subscriber

/**
 * Created by jjj on 2017/7/13.
@description:
 */
open class ProgressSubscriber<T> : Subscriber<T>, ProgressCancelListener {

    var mSubscriberOnNextListener: SubscriberOnNextListener<T>
    var mProgressDialogHandler: ProgressDialogHandler? = null

    var mContext:Context?=null

    constructor(subscriber: SubscriberOnNextListener<T>, context: Context) {
        mSubscriberOnNextListener = subscriber
        mProgressDialogHandler = ProgressDialogHandler(context, this, true)
        mContext=context
    }

    override fun onStart() {
        showProgressDialog()
    }

    override fun onNext(t: T) {
        mSubscriberOnNextListener.onNext(t)
    }

    override fun onCompleted() {
        dismissProgressDialog()
    }

    override fun onError(e: Throwable?) {
        dismissProgressDialog()
        Log.e("call onError", e!!.message)
    }

    override fun onCancelProgress() {
        if (!isUnsubscribed)
            unsubscribe()
    }

    fun showProgressDialog() {
        ToastUtils.show(mContext, "对话框")
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler!!.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget()
        }
    }

    fun dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler!!.obtainMessage(ProgressDialogHandler!!.DISMISS_PROGRESS_DIALOG).sendToTarget()
        }
    }
}
