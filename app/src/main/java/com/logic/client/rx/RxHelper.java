package com.logic.client.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/16
 * @desc
 */

public class RxHelper {

    /**
     * 倒计时
     * @param time
     * @return
     */
    public static Flowable<Integer> countdown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;

        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
