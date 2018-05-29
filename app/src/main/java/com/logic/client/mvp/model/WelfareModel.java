package com.logic.client.mvp.model;

import android.util.Log;

import com.logic.client.bean.GankData;
import com.logic.client.bean.Results;
import com.logic.client.net.OkClient;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.base.mvp.BaseModel;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/27
 * @desc
 */

public class WelfareModel extends BaseModel {

    public Flowable<List<Results>> getData(int page , int size , final List<Results> data){
        return OkClient.getDefault(OkConstants.TYPE_GANK_HOST).getWelfareList(OkClient.getCacheControl(),size,page)
                .toFlowable(BackpressureStrategy.BUFFER)
                .map(new Function<GankData, List<Results>>() {
                    @Override
                    public List<Results> apply(@NonNull GankData gankData) throws Exception {
                        String name = Thread.currentThread().getName();
                        List<Results> results = gankData.getResults();
                        data.addAll(results);
                        return results;
                    }
                });

    }

}
