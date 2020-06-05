package com.sz.jjj.rxjavaretrofitdemo.net;

import com.sz.jjj.baselibrary.base.RxBaseActivity;
import com.sz.jjj.baselibrary.network.manager.ServiceManager;

import io.reactivex.Observable;

/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */

public class MovieManager extends ServiceManager<MovieService> implements MovieService {

    public MovieManager(RxBaseActivity activity, Class<MovieService> t) {
        super(activity, t);
    }

    public static MovieManager get(RxBaseActivity activity) {
        return new MovieManager(activity, MovieService.class);
    }

    @Override
    public Observable getTopMovie(int start, int count, String token) {
        return io_activity(sApiService.getTopMovie(start, count, token));
    }
}
