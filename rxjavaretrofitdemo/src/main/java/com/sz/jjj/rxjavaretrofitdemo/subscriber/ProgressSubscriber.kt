package com.sz.jjj.rxjavaretrofitdemo.subscriber

import android.content.Context
import android.util.Log
import com.sz.jjj.baselibrary.utils.ToastUtils
import com.sz.jjj.rxjavaretrofitdemo.progress.ProgressCancelListener
import com.sz.jjj.rxjavaretrofitdemo.progress.ProgressDialogHandler
import rx.Subscriber
import java.io.IOException
import java.net.UnknownHostException

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
        mProgressDialogHandler = ProgressDialogHandler(mContext!!, this, true)
    }

    constructor(subscriberOnNextListener: SubscriberOnNextListener<T>, context: Context, showProgressDialog: Boolean) {
        mSubscriberOnNextListener = subscriberOnNextListener
        mContext = context
        isShowProgress = showProgressDialog
        mProgressDialogHandler = ProgressDialogHandler(mContext!!, this, true)
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
    override fun onError(throwable: Throwable?) {
        dismissProgressDialog()
        if (throwable != null) {
            Log.e("call", throwable.message)
            var message: String? = null
            if (throwable is IOException || throwable.toString().contains("Unable to resolve host")) {
                message = "请检查网络连接"
            } else if (throwable is UnknownHostException) {
                message = "网络连接较慢，请检查后重试"
            } else if (throwable is IllegalStateException) {
                message = "数据加载失败，请稍后再试"
            } else if (throwable.toString().contains("Canceled")) {
                //取消请求 do nothing
            } else if (throwable.message!!.contains("token")) {
                //token未认证
            }
            ToastUtils.show(mContext, message)
//            mSubscriberOnNextListener!!.onError(throwable)
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
