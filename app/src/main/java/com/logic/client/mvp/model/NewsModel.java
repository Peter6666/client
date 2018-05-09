package com.logic.client.mvp.model;

import com.logic.client.bean.IdataNews;
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

public class NewsModel extends BaseModel {


    public Flowable<IdataNews> getNews(String type, int page, String site){
        return OkClient.getDefault(OkConstants.TYPE_IDATA_HOST).getIndataNews(type,page,site)
                .toFlowable(BackpressureStrategy.BUFFER);
    }


}
