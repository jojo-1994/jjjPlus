package com.sz.jjj.rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.sz.jjj.R
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by jjj on 2018/3/26.
@description:
 */
class RxjavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rxjava_activity)

        var retrofit =  Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava
                .build();

        // b. 创建 网络请求接口 的实例
        var request = retrofit.create(GetRequest_Interface::class.java)

        // c. 采用Observable<...>形式 对 网络请求 进行封装
        var observable = request.getCall();
        // d. 通过线程切换发送网络请求
        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(object: Observer<Translation>{
                    override fun onNext(t: Translation?) {
                        Log.e("next", "next")
                    }

                    override fun onCompleted() {
                        Log.e("completed", "completed")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("error", "error")
                    }

                })
    }
}