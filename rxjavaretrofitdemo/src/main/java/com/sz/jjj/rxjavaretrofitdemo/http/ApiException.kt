package com.sz.jjj.rxjavaretrofitdemo.http

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

    fun getApiExceptionMessage(resultCode: Int): String {
        var message: String = null!!
        when (resultCode) {
            NO_DATA -> message = "暂无相关数据"
        }
        return message
    }


}