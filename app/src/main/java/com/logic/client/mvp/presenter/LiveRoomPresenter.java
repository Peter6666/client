package com.logic.client.mvp.presenter;


import com.logic.client.R;
import com.logic.client.bean.LiveRoom;
import com.logic.client.bean.StreamSrc;
import com.logic.client.mvp.model.LiveRoomModel;
import com.logic.client.mvp.view.activity.LiveRoomActivity;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BasePresenter;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/24
 * @desc
 */

public class LiveRoomPresenter extends BasePresenter<LiveRoomModel, LiveRoomActivity> {

    public void getLiveRoom(String uid) {
        getModel().getLiveRoom(uid)
                .map(new Function<LiveRoom, LiveRoom>() {
                    @Override
                    public LiveRoom apply(@NonNull LiveRoom liveRoom) throws Exception {

                        LiveRoom.LiveBean live = liveRoom.getLive();
                        LiveRoom.LiveBean.WsBean ws = live.getWs();
                        LiveRoom.LiveBean.WsBean.FlvBean flv = ws.getFlv();
                        StreamSrc value = flv.getValue(false);
                        getView().showLive(flv);

                        return liveRoom;
                    }
                })
                .compose(RxSchedulers.<LiveRoom>FlowableToMain())
                .subscribe(new FlowableSubscriber<LiveRoom>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        getView().showLoading(BaseApplication.getResource().getString(R.string.loadding));
                        s.request(1000);

                    }

                    @Override
                    public void onNext(LiveRoom liveRoom) {
                        getView(). returnData(liveRoom);
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
    protected LiveRoomModel initModel() {
        return new LiveRoomModel();
    }
}
