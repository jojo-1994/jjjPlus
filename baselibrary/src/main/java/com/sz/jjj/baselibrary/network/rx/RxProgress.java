package com.sz.jjj.baselibrary.network.rx;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.sz.jjj.baselibrary.network.dialog.DialogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author:jjj
 * @data:2018/5/23
 * @description:
 */

public class RxProgress {

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity, final String msg) {

        final DialogUtils dialogUtils = new DialogUtils();

        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        dialogUtils.showProgress(activity, msg);
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dialogUtils.dismissProgress(activity);
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        return applyProgressBar(activity, "Loading");
    }
}
