package com.sz.jjj.rxjavaretrofitdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sz.jjj.baselibrary.base.RxBaseActivity;
import com.sz.jjj.baselibrary.network.common.DefaultObserver;
import com.sz.jjj.baselibrary.network.manager.OkHttpManager;
import com.sz.jjj.baselibrary.network.manager.RetrofitManager;
import com.sz.jjj.baselibrary.network.rx.RxTransformers;
import com.sz.jjj.rxjavaretrofitdemo.net.MovieManager;
import com.sz.jjj.rxjavaretrofitdemo.net.MovieService;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


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
                .compose(RxTransformers.<Movie>io_activity(MainActivity.this))
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
        MovieManager.getInstance(this)
                .getTopMovie(0, 1, "过期")
                .subscribe(new DefaultObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie o) {
                        tv_message.setText(tv_message.getText() + "\n" + "----" + o.getCount());
                    }
                });
    }

    public void getBySyn(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                HashMap<String, String> map = new HashMap<>();
                map.put("start", "0");
                map.put("count", "2");
                String msg = OkHttpManager.getInstance().requestGetBySyn("top250", map);
                emitter.onNext(msg);
            }
        })
                .compose(RxTransformers.<String>io_main())
                .flatMap(new Function<String, ObservableSource<Movie>>() {
                    @Override
                    public ObservableSource<Movie> apply(String s) throws Exception {
                        final Movie movie=new Gson().fromJson(s, Movie.class);
                        return Observable.fromArray(movie);
                    }
                })
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie s) throws Exception {
                        tv_message.setText(tv_message.getText() + "\n" + "----" + s.getCount());
                    }
                });

    }
}
