package com.logic.client.mvp.model;

import com.logic.client.bean.IdataNews;
import com.logic.client.bean.LiveChannel;
import com.logic.client.net.OkClient;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.base.mvp.BaseModel;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class LiveModel extends BaseModel {


    public Flowable<LiveChannel> getLive(String slug){
        return OkClient.getDefault(OkConstants.TYPE_LIVE_HOST)
                .getLiveListByChannel(slug)
                .toFlowable(BackpressureStrategy.BUFFER);
    }


}
