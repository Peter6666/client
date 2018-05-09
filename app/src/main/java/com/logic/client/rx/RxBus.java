package com.logic.client.rx;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.logic.client.mvp.view.fragment.HomeFragment;

import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/13
 * @desc 有异常处理的 Rxbus 和无异常处理的 Rxbus（注释掉）
 */

public class RxBus {

    private static volatile RxBus intance = null;

    private final Relay<Object> mBus;
    //    private final Subject<Object> mBus;
    private HashMap<String, CompositeDisposable> mSubscriptionMapper;

    public RxBus() {
//        mBus = PublishSubject.create().toSerialized();
        mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getIntance() {
        if (intance == null) {
            synchronized (RxBus.class) {
                if (intance == null) {
                    intance = new RxBus();
                }
            }
        }
        return intance;
    }

    /**
     * 发送事件
     *
     * @param val
     */
    public void post(Object val) {
        mBus.accept(val);
//        mBus.onNext(val);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    /**
     * @param eClass
     * @param <T>
     * @return 返回指定的Observable
     */
    public <T> Observable<T> toObservable(Class<T> eClass) {
        return mBus.ofType(eClass);
    }

    /**
     * @param eClass
     * @param <T>
     * @return 返回指定的带背压Flowable
     */
    public <T> Flowable<T> getObservable(Class<T> eClass) {
        return toObservable(eClass).
                toFlowable(BackpressureStrategy.BUFFER);//背压
    }

    /**
     * @param type
     * @param next
     * @param error
     * @param <T>
     * @return 一个默认的订阅方法
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);

    }

    /**
     * 注册subscription
     *
     * @param val
     * @param disposable
     */
    public void addSubscription(Object val, Disposable disposable) {
        if (mSubscriptionMapper == null) {
            mSubscriptionMapper = new HashMap<>();
        }
        String key = val.getClass().getName();
        if (mSubscriptionMapper.get(key) != null) {
            mSubscriptionMapper.get(key).add(disposable);
        } else {
            CompositeDisposable compositeSubscription = new CompositeDisposable();
            compositeSubscription.add(disposable);
            mSubscriptionMapper.put(key, compositeSubscription);
        }
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    /**
     * 取消订阅
     *
     * @param val
     */
    public void unSubscribe(Object val) {
        if (mSubscriptionMapper == null) {
            return;
        }

        String key = val.getClass().getName();
        if (!mSubscriptionMapper.containsKey(key)) {
            return;
        }
        if (mSubscriptionMapper.get(key) != null) {
            mSubscriptionMapper.get(key).dispose();
        }

        mSubscriptionMapper.remove(key);
    }

    public void unSubscribe(){
        if (mSubscriptionMapper == null) {
            return;
        }
        mSubscriptionMapper.clear();

    }

}
