package com.sz.jjj.rxjavaretrofitdemo.progress

import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import android.os.Message

/**
 * Created by jjj on 2017/7/14.
@description:
 */

class ProgressDialogHandler(private val context: Context, private val mProgressCancelListener: ProgressCancelListener,
                            private val cancelable: Boolean) : Handler() {


    // companion object 相当于静态内部类
    companion object {
        val SHOW_PROGRESS_DIALOG = 1
        val DISMISS_PROGRESS_DIALOG = 2
    }

    private var pd: ProgressDialog? = null

    private fun initProgressDialog() {
        if (pd == null) {
            pd = ProgressDialog(context)

            pd!!.setCancelable(cancelable)

            if (cancelable) {
                pd!!.setOnCancelListener { mProgressCancelListener.onCancelProgress() }
            }

            if (!pd!!.isShowing) {
                pd!!.show()
            }
        }
    }

    private fun dismissProgressDialog() {
        if (pd != null) {
            pd!!.dismiss()
            pd = null
        }
    }

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            SHOW_PROGRESS_DIALOG -> initProgressDialog()
            DISMISS_PROGRESS_DIALOG -> dismissProgressDialog()
        }
    }

}
