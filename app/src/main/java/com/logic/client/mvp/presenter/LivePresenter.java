package com.logic.client.mvp.presenter;

import android.util.Log;

import com.logic.client.R;
import com.logic.client.app.AppConstants;
import com.logic.client.bean.IdataNews;
import com.logic.client.bean.LiveChannel;
import com.logic.client.mvp.model.LiveModel;
import com.logic.client.mvp.model.NewsModel;
import com.logic.client.mvp.view.fragment.LiveFragment;
import com.logic.client.mvp.view.fragment.NewsFragment;
import com.logic.client.net.OkClient;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BasePresenter;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class LivePresenter extends BasePresenter<LiveModel, LiveFragment> {

    public void getLive(final String slug) {

        getModel().getLive(slug)
                .compose(getView().<LiveChannel>bindToLife(FragmentEvent.PAUSE))
                .map(new Function<LiveChannel, LiveChannel>() {
                    @Override
                    public LiveChannel apply(@NonNull LiveChannel liveChannel) throws Exception {
                        List<LiveChannel.BigsquareBean> bigsquare = liveChannel.getBigsquare();
                        List<LiveChannel.DataBean> data = liveChannel.getData();
                        if (data != null && data.size() > 0)
                            getView().returnLiveData(data);
                        if (bigsquare != null && bigsquare.size() > 0)
                            getView().returnBanner(bigsquare);
                            return liveChannel;
                    }
                })
                .compose(RxSchedulers.<LiveChannel>FlowableToMain())
                .subscribe(new FlowableSubscriber<LiveChannel>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        getView().showLoading(BaseApplication.getResource().getString(R.string.loadding));
                        s.request(1000);
                    }

                    @Override
                    public void onNext(LiveChannel liveChannel) {
                            getView().returnLiveChannel(liveChannel);
                        Log.i("ceshi","onNext" +slug);
                    }

                    @Override
                    public void onError(Throwable t) {
                        getView().showError(t.toString());
                    }

                    @Override
                    public void onComplete() {
                        getView().stopLoading();
                    }
                });

    }

    public void ScrollToTop() {
        Disposable event = RxBus.getIntance().doSubscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                if (o.equals(AppConstants.LIVE_TO_TOP)) {
                    getView().ScrollToTop();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        RxBus.getIntance().addSubscription(AppConstants.LIVE_TO_TOP, event);
    }

    @Override
    public void onStart() {
        super.onStart();
        ScrollToTop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getIntance().unSubscribe(AppConstants.LIVE_TO_TOP);
    }

    @Override
    protected LiveModel initModel() {
        return new LiveModel();
    }
}
