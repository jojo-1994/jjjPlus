package com.sz.jjj.rxjavaretrofitdemo.model


/**
 * Created by jjj on 2017/7/13.
@description:
 */
class HttpResult<T> {
    // 大部分Http服务差不多这么设置
//    var resultCode: Int? = null
//    var resultMessage: String? = null
//    var data: T? = null

    // 模仿这种形式
    public var count: Int = 0
    public var start: Int = 0
    public var total: Int = 0
    public var title: String? = null

    //用来模仿Data
    public val subjects: T? = null
}