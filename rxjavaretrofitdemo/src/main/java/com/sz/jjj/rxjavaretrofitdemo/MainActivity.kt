package com.sz.jjj.rxjavaretrofitdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.sz.jjj.baselibrary.utils.ToastUtils
import com.sz.jjj.rxjavaretrofitdemo.http.HttpMethods
import com.sz.jjj.rxjavaretrofitdemo.model.HttpResult
import com.sz.jjj.rxjavaretrofitdemo.model.MovieEntity
import com.sz.jjj.rxjavaretrofitdemo.model.Subjects
import com.sz.jjj.rxjavaretrofitdemo.service.MovieService
import com.sz.jjj.rxjavaretrofitdemo.subscriber.ProgressSubscriber
import com.sz.jjj.rxjavaretrofitdemo.subscriber.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory.create
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by jjj on 2017/7/13.
@description:
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_retrofit.setOnClickListener() {
            getMovie()
        }
        tv_rxjava.setOnClickListener() {
            getMovieForRxjava()
        }
        tv_httpMethods.setOnClickListener() {
            getMovieForHttpMethods()
        }
        tv_httpResult.setOnClickListener() {
            getMovieForHttpResult()
        }
        tv_subscribe.setOnClickListener() {
            getMovieForScbscriber()
        }
    }

    // 1、只是用retrofit进行网络请求
    private fun getMovie() {
        val baseUrl = "https://api.douban.com/v2/movie/"
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(create())
                .build()
        val movieService = retrofit.create(MovieService::class.java)
        val call = movieService.getTopMovie(0, 1)

        call.enqueue(object : Callback<MovieEntity> {
            override fun onFailure(call: Call<MovieEntity>?, t: Throwable?) {
                Log.e("call failure", t.toString())
            }

            override fun onResponse(call: Call<MovieEntity>?, response: Response<MovieEntity>?) {
                Log.e("call onResponse", response.toString())
                Log.e("call onResponse", response!!.body().toString())
            }
        })

    }

    // 2、retrofit+rxjava进行网络请求
    private fun getMovieForRxjava() {
        val baseUrl = "https://api.douban.com/v2/movie/"
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        val movieService = retrofit.create(MovieService::class.java)

        movieService.getTopMovie2(0, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<MovieEntity>() {
                    override fun onNext(t: MovieEntity?) {
                        Log.e("call onSuccess", t.toString())
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("call onError", e!!.message)
                    }

                    override fun onCompleted() {
                        ToastUtils.show(this@MainActivity, "Get Top Movie Completed")
                    }
                })

    }

    // 3、使用封装的请求过程
    private fun getMovieForHttpMethods() {
        val httpMethods = HttpMethods()
        httpMethods.getTopMovie(object : Subscriber<MovieEntity>() {
            override fun onNext(t: MovieEntity?) {
                Log.e("call onSuccess", t.toString())
            }

            override fun onCompleted() {
                ToastUtils.show(this@MainActivity, "Get Top Movie Completed")
            }

            override fun onError(e: Throwable?) {
                Log.e("call onError", e!!.message)
            }
        }, 0, 1)
    }

    // 4、使用封装的请求数据
    private fun getMovieForHttpResult() {
        val subscribe = object : Subscriber<HttpResult<List<Subjects>>>() {
            override fun onError(e: Throwable?) {
                Log.e("call onError", e!!.message)
            }

            override fun onCompleted() {
                ToastUtils.show(this@MainActivity, "Get Top Movie Completed")
            }

            override fun onNext(t: HttpResult<List<Subjects>>?) {
                Log.e("call onSuccess", t!!.subjects.toString())
            }

        }

        val httpMethods = HttpMethods()
        httpMethods.getTopMovie2(subscribe, 0, 1)
    }

    // 5、ProgressDialog的Subscriber
    private fun getMovieForScbscriber() {

        val subscribeListener = object : SubscriberOnNextListener<List<Subjects>> {
            //接受到请求结果之后对UI的处理
            override fun onNext(subjects: List<Subjects>) {
                ToastUtils.show(this@MainActivity, subjects.get(0).toString())
            }
        }
        val progressScriber = object : ProgressSubscriber<List<Subjects>>(subscribeListener) {}
        val httpMethods = HttpMethods()
        httpMethods.getTopMovie3(progressScriber, 0, 1)
    }
}

