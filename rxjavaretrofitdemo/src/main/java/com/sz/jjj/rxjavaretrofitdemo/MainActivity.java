package com.sz.jjj.rxjavaretrofitdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sz.jjj.baselibrary.base.RxBaseActivity;
import com.sz.jjj.baselibrary.network.common.DefaultObserver;
import com.sz.jjj.baselibrary.network.rx.RxTransformers;
import com.sz.jjj.rxjavaretrofitdemo.net.RetrofitHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by jjj on 2018/5/17.
 *
 * @description:
 */

public class MainActivity extends RxBaseActivity {
    TextView tv_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = findViewById(R.id.tv_message);
        findViewById(R.id.tv_subscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovie();
            }
        });
        findViewById(R.id.tv_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime();
            }
        });
    }

    private void getMovie() {
//        RetrofitHelper.getApiService()
//                .getTopMovie(0, 1)
//                .flatMap(new Function<Movie, ObservableSource<Movie>>() {
//                    @Override
//                    public ObservableSource<Movie> apply(Movie movie) throws Exception {
//                        return RetrofitHelper.getApiService()
//                                .getTopMovie(0, 1)
//                                .compose(RxTransformers.<Movie>no_progress(MainActivity.this))
//                                .doOnNext(new Consumer<Movie>() {
//                                    @Override
//                                    public void accept(Movie movie) throws Exception {
//                                        tv_message.setText(tv_message.getText() + "\n" + "11111111");
//                                    }
//                                });
//                    }
//                })
//                .flatMap(new Function<Movie, ObservableSource<Movie>>() {
//                    @Override
//                    public ObservableSource<Movie> apply(Movie movie) throws Exception {
//                        return RetrofitHelper.getApiService()
//                                .getTopMovie(0, 1)
//                                .compose(RxTransformers.<Movie>no_progress(MainActivity.this))
//                                .doOnNext(new Consumer<Movie>() {
//                                    @Override
//                                    public void accept(Movie movie) throws Exception {
//                                        tv_message.setText(tv_message.getText() + "\n" + "2222222");
//                                    }
//                                });
//                    }
//                })
//                .compose(RxTransformers.<Movie>io_activity(this))
//                .subscribe(new DefaultObserver<Movie>() {
//                    @Override
//                    public void onSuccess(Movie movie) {
//                        tv_message.setText(tv_message.getText() + "\n" + "3333333");
//                    }
//                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RetrofitHelper.getApiService()
                        .getTopMovie(0, 1)
                        .delay(5, TimeUnit.SECONDS)
                        .compose(RxTransformers.<Movie>io_activity(MainActivity.this))
                        .subscribe(new DefaultObserver<Movie>() {
                            @Override
                            public void onSuccess(Movie response) {
                                tv_message.setText(tv_message.getText() + "\n" + "444444444");
                            }
                        });
            }
        }, 0);//3秒后执行Runnable中的run方法

    }

    private void startTime() {
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .take(3).subscribe(new Observer<Long>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.e("dddddd", "start");
            }

            @Override
            public void onNext(Long aLong) {
                Log.e("dddddd", System.currentTimeMillis() + "");
                if (aLong == 1) {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("dddddd", "end");
            }
        });

    }
}
