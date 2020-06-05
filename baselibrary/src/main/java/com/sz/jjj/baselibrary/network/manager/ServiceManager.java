package com.sz.jjj.baselibrary.network.manager;

import com.sz.jjj.baselibrary.base.RxBaseActivity;
import com.sz.jjj.baselibrary.network.rx.RxTransformers;

import io.reactivex.Observable;

/**
 * @author:jjj
 * @data:2018/5/31
 * @description:
 */

public abstract class ServiceManager<T> {

    protected RxBaseActivity mActivity;
    protected T sApiService;

    public ServiceManager(RxBaseActivity activity, Class<T> t) {
        mActivity = activity;
        sApiService = RetrofitManager.create(t);
    }

    /**
     * 带进度条的observable
     *
     * @param observable
     * @param <O>
     * @return
     */
    protected <O> Observable<O> io_activity(Observable<O> observable) {
        return observable.compose(RxTransformers.<O>io_activity(mActivity));
    }

    /**
     * 不带进度条的observable
     *
     * @param observable
     * @param <O>
     * @return
     */
    protected <O> Observable<O> no_progress(Observable<O> observable) {
        return observable.compose(RxTransformers.<O>no_progress(mActivity));
    }

    /**
     * io线程操作的observable
     *
     * @param observable
     * @param <O>
     * @return
     */
    protected <O> Observable<O> io_io(Observable<O> observable) {
        return observable.compose(RxTransformers.<O>io_io());
    }

    /**
     * io->ui线程的observable
     *
     * @param observable
     * @param <O>
     * @return
     */
    protected <O> Observable<O> io_main(Observable<O> observable) {
        return observable.compose(RxTransformers.<O>io_main());
    }


}
