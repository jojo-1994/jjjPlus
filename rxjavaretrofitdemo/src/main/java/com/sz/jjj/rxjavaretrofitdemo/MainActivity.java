package com.sz.jjj.rxjavaretrofitdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sz.jjj.baselibrary.base.RxBaseActivity;
import com.sz.jjj.baselibrary.network.common.DefaultObserver;
import com.sz.jjj.baselibrary.network.manager.OkHttpManager;
import com.sz.jjj.baselibrary.network.manager.RetrofitManager;
import com.sz.jjj.baselibrary.network.progress.IProgressListener;
import com.sz.jjj.baselibrary.network.rx.RxTransformers;
import com.sz.jjj.rxjavaretrofitdemo.net.MovieManager;
import com.sz.jjj.rxjavaretrofitdemo.net.MovieService;

import java.util.HashMap;

import io.reactivex.Observable;


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
    }

    /**
     * 简化前get写法
     */
    public void get1(View view) {
        RetrofitManager.create(MovieService.class)
                .getTopMovie(0, 1, "")
                .compose(RxTransformers.io_activity(MainActivity.this))
                .subscribe(new DefaultObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie movie) {
                        tv_message.setText(tv_message.getText() + "\n" + "----" + movie.getCount());
                    }
                });

    }

    /**
     * 简化后get写法
     */
    public void get2(View view) {
        MovieManager
                .get(this)
                .getTopMovie(0, 1, "过期")
                .subscribe(new DefaultObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie o) {
                        tv_message.setText(tv_message.getText() + "\n" + "----" + o.getCount());
                    }
                });
    }

    public void getBySyn(View view) {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("start", "0");
//                map.put("count", "2");
//                String msg = OkHttpManager.getInstance().requestGetBySyn("top250", map);
//                emitter.onNext(msg);
//            }
//        })
//                .compose(RxTransformers.<String>io_main())
//                .flatMap(new Function<String, ObservableSource<Movie>>() {
//                    @Override
//                    public ObservableSource<Movie> apply(String s) throws Exception {
//                        final Movie movie = new Gson().fromJson(s, Movie.class);
//                        return Observable.fromArray(movie);
//                    }
//                })
//                .subscribe(new Consumer<Movie>() {
//                    @Override
//                    public void accept(Movie s) throws Exception {
//                        tv_message.setText(tv_message.getText() + "\n" + "----" + s.getCount());
//                    }
//                });

        Observable.create(emitter -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("start", "0");
            map.put("count", "2");
            String msg = OkHttpManager.getInstance().requestGetBySyn("top250", map);
            emitter.onNext(msg);
        }).compose(RxTransformers.io_main()).flatMap(o -> {
            final Movie movie = new Gson().fromJson((String) o, Movie.class);
            return Observable.fromArray(movie);
        }).subscribe(movie -> {
            tv_message.setText(tv_message.getText() + "\n" + "----" + movie.getCount());
        });

    }

    public void download(View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                OkHttpManager.getInstance().download("http://oh0vbg8a6.bkt.clouddn.com/app-debug.apk", null, new IProgressListener() {
                    @Override
                    public void onProgress(long currentBytes, long contentLength, boolean done) {
//                        Log.e("TAG", contentLength + "");
//                        Log.e("TAG", "contentLength:" + contentLength);
                    }
                });
            }
        });

//        Observable.create(new ObservableOnSubscribe<Long>() {
//            @Override
//            public void subscribe(final ObservableEmitter<Long> emitter) throws Exception {
//                OkHttpManager.getInstance().download("http://oh0vbg8a6.bkt.clouddn.com/app-debug.apk", null, new IProgressListener() {
//                    @Override
//                    public void onProgress(long currentBytes, long contentLength, boolean done) {
//                    }
//                });
//                emitter.onNext(1L);
//            }
//        })
//                .compose(RxTransformers.<Long>io_main())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long s) throws Exception {
//                    }
//                });

    }
}
