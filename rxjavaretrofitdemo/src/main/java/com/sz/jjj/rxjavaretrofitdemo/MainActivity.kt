package com.sz.jjj.rxjavaretrofitdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sz.jjj.baselibrary.utils.ToastUtils
import com.sz.jjj.rxjavaretrofitdemo.http.HttpMethods
import com.sz.jjj.rxjavaretrofitdemo.model.Subjects
import com.sz.jjj.rxjavaretrofitdemo.subscriber.ProgressSubscriber
import com.sz.jjj.rxjavaretrofitdemo.subscriber.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_main.*




/**
 * Created by jjj on 2017/7/13.
@description:
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_subscribe.setOnClickListener() {
            getMovie()
        }

    }

    class A(){

    }

    class B(val a:A){

    }

    private fun getMovie() {

        // 单例模式：object: +对象名
        val subscribeListener = object : SubscriberOnNextListener<List<Subjects>> {
            override fun onComplete() {

            }

            //接受到请求结果之后对UI的处理
            override fun onNext(subjects: List<Subjects>) {
                tv_message.setText(subjects.get(0).toString())
            }


            override fun onError(e: Throwable) {
                ToastUtils.show(this@MainActivity, e.message)
            }
        }
        val progressScriber = object : ProgressSubscriber<List<Subjects>>(subscribeListener, this, true) {}
        val httpMethods = HttpMethods()
        httpMethods.getTopMovie(progressScriber, 0, 1)
    }

}

