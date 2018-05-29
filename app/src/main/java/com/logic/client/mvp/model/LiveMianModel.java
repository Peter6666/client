package com.logic.client.mvp.model;

import com.logic.client.bean.IdataNews;
import com.logic.client.bean.LiveChannelTabs;
import com.logic.client.net.OkClient;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.mvp.BaseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/23
 * @desc
 */

public class LiveMianModel extends BaseModel {


    public Flowable<List<LiveChannelTabs>> getLiveChannelTabs(){
        return OkClient.getDefault(OkConstants.TYPE_LIVE_HOST).getLiveChannelTabs(OkClient.getCacheControl())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

}
