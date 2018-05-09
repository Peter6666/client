package com.logic.client.rx;

import com.trello.rxlifecycle2.LifecycleTransformer;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/16
 * @desc
 */

public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> toMain() {

        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> FlowableTransformer<T, T> FlowableToMain() {

        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }



}
