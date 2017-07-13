package com.sz.jjj.rxjavaretrofitdemo.http

/**
 * Created by jjj on 2017/7/13.
@description:
 */
open class ApiException: RuntimeException {

    // 次构造函数
    constructor(resultCode: Int){

    }

    constructor(detailMessage: String){

    }
}