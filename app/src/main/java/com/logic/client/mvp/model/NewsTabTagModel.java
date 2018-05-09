package com.logic.client.mvp.model;

import android.util.Log;

import com.logic.client.R;
import com.logic.client.bean.NewsChannelTabs;
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

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/2
 * @desc
 */

public class NewsTabTagModel extends BaseModel {


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

    public Flowable<List<NewsChannelTabs>> getUnSelectChannel() {

        return Flowable.create(new FlowableOnSubscribe<List<NewsChannelTabs>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<List<NewsChannelTabs>> e) throws Exception {
                ArrayList<NewsChannelTabs> tabs = (ArrayList<NewsChannelTabs>) CacheUtils.getInstance().getSerializable("ADD_CHANNEL");
                if (tabs == null) {
                    tabs = loadTabs(R.array.news_channel_name, false, false, NewsChannelTabs.ADD_CHANNEL);//加载频道
                    CacheUtils.getInstance().put("ADD_CHANNEL", tabs);
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

    /**
     * @param current
     * @param add
     */
    public void saveChannel(final ArrayList<NewsChannelTabs> current, final ArrayList<NewsChannelTabs> add) {

        if (current != null)
            CacheUtils.getInstance().put("CURRENT_CHANNEL", current);
        if (add != null)
            CacheUtils.getInstance().put("ADD_CHANNEL", add);
    }

}
