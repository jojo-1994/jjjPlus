package com.sz.jjj.rxjavaretrofitdemo.http

import android.content.Context
import com.sz.jjj.baselibrary.utils.ToastUtils
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by jjj on 2017/7/13.
@description:
 */
open class ApiException : RuntimeException {

    companion object {
        val NO_DATA = 100
    }

    // 次构造函数
    constructor(resultCode: Int) {
        getApiExceptionMessage(resultCode)
    }

    constructor(throwable: Throwable, mContext: Context) {
        var message: String? = null
        if (throwable != null) {
            when (throwable) {
                is IOException -> message = "请检查网络连接"
                is UnknownHostException -> message = "网络连接较慢，请检查后重试"
                is IllegalStateException -> message = "数据加载失败，请稍后再试"
            }

            if (throwable.toString().contains("Canceled")) {
                //取消请求 do nothing
            } else if (throwable.message!!.contains("token")) {
                //token未认证
            }
            ToastUtils.show(mContext, message)
        }
    }

    fun getApiExceptionMessage(resultCode: Int): String {
        var message: String = null!!
        when (resultCode) {
            NO_DATA -> message = "暂无相关数据"
        }
        return message
    }


}