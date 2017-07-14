package com.sz.jjj.rxjavaretrofitdemo.subscriber

import android.content.Context
import com.sz.jjj.rxjavaretrofitdemo.progress.ProgressCancelListener
import com.sz.jjj.rxjavaretrofitdemo.progress.ProgressDialogHandler
import rx.Subscriber

/**
 * Created by jjj on 2017/7/13.
@description:
 */
open class ProgressSubscriber<T> : Subscriber<T>, ProgressCancelListener {

    var mProgressDialogHandler: ProgressDialogHandler? = null
    var mSubscriberOnNextListener: SubscriberOnNextListener<T>? = null
    var mContext: Context? = null
    var isShowProgress: Boolean = true

    constructor(subscriberOnNextListener: SubscriberOnNextListener<T>, context: Context) {
        mSubscriberOnNextListener = subscriberOnNextListener
        mContext = context
        mProgressDialogHandler= ProgressDialogHandler(mContext!!, this, true)
    }

    constructor(subscriberOnNextListener: SubscriberOnNextListener<T>, context: Context, showProgressDialog: Boolean) {
        mSubscriberOnNextListener = subscriberOnNextListener
        mContext = context
        isShowProgress = showProgressDialog
        mProgressDialogHandler= ProgressDialogHandler(mContext!!, this, true)
    }

    // 请求开始：显示加载框
    override fun onStart() {
        if (isShowProgress)
            showProgressDialog()
    }

    // 请求成功：传递数据
    override fun onNext(t: T) {
        mSubscriberOnNextListener!!.onNext(t)
    }

    // 请求完毕：关闭加载框
    override fun onCompleted() {
        dismissProgressDialog()
    }

    // 请求错误：关闭加载框，提示
    override fun onError(e: Throwable?) {
        dismissProgressDialog()
//        throw ApiException(e!!, mContext!!)
        if (e != null) {
            mSubscriberOnNextListener!!.onError(e)
        }
    }

    // 取消请求
    override fun onCancelProgress() {
        if (!isUnsubscribed)
            unsubscribe()
    }

    // 显示对话框
    fun showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler!!.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget()
        }
    }

    // 关闭对话框
    fun dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler!!.obtainMessage(ProgressDialogHandler!!.DISMISS_PROGRESS_DIALOG).sendToTarget()
        }
    }
}
