package com.logic.client.mvp.presenter;

import com.logic.client.R;
import com.logic.client.app.AppConstants;
import com.logic.client.bean.LiveChannelTabs;
import com.logic.client.mvp.model.LiveMianModel;
import com.logic.client.mvp.view.fragment.LiveMianFragment;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BasePresenter;
import com.logic.client.rx.base.mvp.IModel;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/23
 * @desc
 */

public class LiveMianPresenter extends BasePresenter<LiveMianModel,LiveMianFragment> {

    public void getLiveChannelTabs(){
        getModel().getLiveChannelTabs()
                .compose(getView().<List<LiveChannelTabs>>bindToLifecycle())
                .compose(RxSchedulers.<List<LiveChannelTabs>>FlowableToMain())
                .subscribe(new FlowableSubscriber<List<LiveChannelTabs>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        getView().showLoading(BaseApplication.getResource().getString(R.string.loadding));
                        s.request(1000);
                    }

                    @Override
                    public void onNext(List<LiveChannelTabs> liveChannelTabses) {
                        if (liveChannelTabses.size()>0)
                            getView().returnSelectChannel(liveChannelTabses);


                        getView().returnSelectChannel(liveChannelTabses);
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

    @Override
    protected LiveMianModel initModel() {
        return new LiveMianModel();
    }

    public void toTop() {
        RxBus.getIntance().post(AppConstants.NEWS_TO_TOP);
    }
}
