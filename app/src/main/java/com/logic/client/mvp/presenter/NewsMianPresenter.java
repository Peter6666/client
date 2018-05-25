package com.logic.client.mvp.presenter;

import com.logic.client.app.AppConstants;
import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.mvp.model.NewsMianModel;
import com.logic.client.mvp.view.fragment.NewsMianFragment;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.base.mvp.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public class NewsMianPresenter extends BasePresenter<NewsMianModel, NewsMianFragment> {


//    public void getdata( final List<Results> results, int page, int size) {
//
//        getModel().getData(size, page, results)
//                .compose(RxSchedulers.<List<Results>>FlowableToMain())
//                .subscribe(new FlowableSubscriber<List<Results>>() {
//                    private Subscription s;
//
//                    @Override
//                    public void onSubscribe(@NonNull Subscription s) {
//                        //1， onSubscribe 是2.x新添加的方法，在发射数据前被调用，相当于1.x的onStart方法
//                        //2， 参数为  Subscription ，Subscription 可用于向上游请求发射多少个元素，也可用于取笑请求
//                        //3,  必须要调用Subscription 的request来请求发射数据，不然上游是不会发射数据的。
//                        this.s = s;
//                        this.s.request(100);
//                    }
//
//                    @Override
//                    public void onNext(List<Results> data) {
//                        if (data.size() <= 0)
//                            getView().mAdapter.loadMoreEnd();
//                        else
//                            getView().mAdapter.loadMoreComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                        getView().mAdapter.notifyDataSetChanged();
//                        getView().mAdapter.loadMoreFail();//上拉加载
//                        t.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        getView().mAdapter.notifyDataSetChanged();
//                    }
//                });
//    }

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
//                getView().newsTabTagAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getIntance().unSubscribe(AppConstants.TAB);
    }

    @Override
    protected NewsMianModel initModel() {
        return new NewsMianModel();
    }

    public void toTop() {
        RxBus.getIntance().post(AppConstants.NEWS_TO_TOP);
    }

    public void backChannel() {
        Disposable event = RxBus.getIntance().doSubscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                 if (o.equals(AppConstants.TAB)){
                   getSelectChannel();
               }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        RxBus.getIntance().addSubscription(AppConstants.TAB,event);
    }
}
