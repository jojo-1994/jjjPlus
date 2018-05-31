package com.sz.jjj.rxjavaretrofitdemo.net;


import com.sz.jjj.rxjavaretrofitdemo.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jjj on 2018/5/17.
 *
 * @description:
 */

public interface MovieService {
    @GET("top250")
    Observable<Movie> getTopMovie(@Query("start") int start, @Query("count") int count, @Query("token") String token);
}
