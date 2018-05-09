package com.logic.client.mvp.presenter;

import android.widget.Toast;

import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.mvp.model.NewsTabTagModel;
import com.logic.client.mvp.view.activity.NewsTabTagActivity;
import com.logic.client.rx.base.mvp.BasePresenter;
import com.logic.client.rx.base.mvp.IModel;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/2
 * @desc
 */

public class NewsTabTagPresenter extends BasePresenter<NewsTabTagModel,NewsTabTagActivity> {
    @Override
    protected NewsTabTagModel initModel() {
        return new NewsTabTagModel();
    }

    public void getSelectChannel(){
        getModel().getSelectChannel().subscribe(new FlowableSubscriber<List<NewsChannelTabs>>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                s.request(1000);
            }

            @Override
            public void onNext(List<NewsChannelTabs> newsChannelTabses) {
                    getView().returnSelectChannel(newsChannelTabses);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                getView().newsTabTagAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getUnSelectChannel(){
        getModel().getUnSelectChannel().subscribe(new FlowableSubscriber<List<NewsChannelTabs>>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                s.request(1000);
            }

            @Override
            public void onNext(List<NewsChannelTabs> newsChannelTabses) {
                getView().returnUnSelectChannel(newsChannelTabses);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                getView().newsTabTagAdapter.notifyDataSetChanged();
            }
        });
    }

    public void saveChannel(final ArrayList<NewsChannelTabs> current, final ArrayList<NewsChannelTabs> add){
        getModel().saveChannel(current,add);
    }

}
