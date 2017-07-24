package com.sz.jjj.rxjavaretrofitdemo.http

import com.sz.jjj.rxjavaretrofitdemo.model.HttpResult
import com.sz.jjj.rxjavaretrofitdemo.model.Subjects
import com.sz.jjj.rxjavaretrofitdemo.service.MovieService
import com.sz.jjj.rxjavaretrofitdemo.subscriber.ProgressSubscriber
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by jjj on 2017/7/13.
@description: 网络请求的封装
 */
class HttpMethods {
    val BASE_URL = "https://api.douban.com/v2/movie/"
    val DEFAULT_TIMEOUT: Long = 5

    val retrofit: Retrofit
    val movieService: MovieService

    // 初始化模块 //https://www.kotlincn.net/docs/reference/classes.html
    init {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        movieService = retrofit.create(MovieService::class.java)
    }

    fun getTopMovie(subscribe: ProgressSubscriber<List<Subjects>>, start: Int, end: Int) {
        movieService.getTopMovie3(start, end)
                .map(HttpResultFunc<List<Subjects>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe)
    }

    private fun toSubscribe(o: Observable<*>, s: Subscriber<*>) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    class HttpResultFunc<T>() : Func1<HttpResult<T>, T> {
        override fun call(t: HttpResult<T>?): T {
            if (t!!.count == 0) {
                throw ApiException(ApiException.NO_DATA)
            }
            return t.subjects!!
        }
    }
}