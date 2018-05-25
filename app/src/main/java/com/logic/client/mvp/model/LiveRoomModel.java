package com.logic.client.mvp.model;

import com.logic.client.bean.LiveRoom;
import com.logic.client.net.OkClient;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.base.mvp.BaseModel;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/24
 * @desc
 */

public class LiveRoomModel extends BaseModel {

    public Flowable<LiveRoom> getLiveRoom(String uid){

        return OkClient.getDefault(OkConstants.TYPE_LIVE_HOST)
                .getLiveRoom(uid)
                .toFlowable(BackpressureStrategy.BUFFER);

    }

}
