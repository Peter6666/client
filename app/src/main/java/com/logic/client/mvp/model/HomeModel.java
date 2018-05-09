package com.logic.client.mvp.model;

import android.util.Log;

import com.logic.client.R;
import com.logic.client.bean.GankData;
import com.logic.client.bean.IdataNews;
import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.bean.Results;
import com.logic.client.net.OkClient;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BaseModel;
import com.logic.client.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public class HomeModel extends BaseModel {

//    public Flowable<List<Results>> getData(int page , int size , final List<Results> data){
//        return OkClient.getDefault(OkConstants.TYPE_GANK_HOST).getWelfareList(size,page)
//                .toFlowable(BackpressureStrategy.BUFFER)
//                .map(new Function<GankData, List<Results>>() {
//                    @Override
//                    public List<Results> apply(@NonNull GankData gankData) throws Exception {
//                        String name = Thread.currentThread().getName();
//                        List<Results> results = gankData.getResults();
//                        data.addAll(results);
//                        return results;
//                    }
//                });
//    }

//    public Flowable<IdataNews> getNews(String type,int page, String site){
//       return OkClient.getDefault(OkConstants.TYPE_IDATA_HOST).getIndataNews(type,page,site)
//                .toFlowable(BackpressureStrategy.BUFFER);
//    }

    /**
     * 获取频道
     * @return
     */
    public Flowable<List<NewsChannelTabs>> getSelectChannel() {
        return Flowable.create(new FlowableOnSubscribe<List<NewsChannelTabs>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<List<NewsChannelTabs>> e) throws Exception {
                ArrayList<NewsChannelTabs> tabs = (ArrayList<NewsChannelTabs>) CacheUtils.getInstance().getSerializable("CURRENT_CHANNEL");
                if (tabs == null) {
                    tabs = loadTabs(R.array.static_news_channel_name, true, true, NewsChannelTabs.CURRENT_CHANNEL);//加载固定频道
                    CacheUtils.getInstance().put("CURRENT_CHANNEL", tabs);
                }

                e.onNext(tabs);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).compose(RxSchedulers.<List<NewsChannelTabs>>FlowableToMain());
    }


    /**
     * 加载频道
     *
     * @return
     */
    public ArrayList<NewsChannelTabs> loadTabs(int id, boolean isSelect, boolean isFixed, int type) {
        String[] stringArray = BaseApplication.getAppContext().getResources().getStringArray(id);
        ArrayList<NewsChannelTabs> tabs = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            NewsChannelTabs newsChannelTabs = new NewsChannelTabs(stringArray[i], isSelect, i, isFixed, type);
            tabs.add(newsChannelTabs);
        }
        return tabs;
    }

    public void getTabTag(){
        return ;
    }

}
