package com.sz.jjj.baselibrary.network.rx;


import com.sz.jjj.baselibrary.base.RxBaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:jjj
 * @data:2018/5/21
 * @description: rxjava 操作符集合
 */

public class RxTransformers {

    public static <T> ObservableTransformer<T, T> io_io() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> io_activity(final RxBaseActivity activity) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.compose(RxTransformers.<T>io_main())
                        .compose(activity.<T>bindToLifecycle())
                        .compose(RxProgress.<T>applyProgressBar(activity));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> no_progress(final RxBaseActivity activity) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.compose(RxTransformers.<T>io_main())
                        .compose(activity.<T>bindToLifecycle());
            }
        };
    }

}
