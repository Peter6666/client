package com.logic.client.mvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.logic.client.adapter.WelfareAdapter;
import com.logic.client.bean.Results;
import com.logic.client.mvp.model.WelfareModel;
import com.logic.client.mvp.view.fragment.WelfareFragment;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.mvp.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/27
 * @desc
 */

public class WelfarePresenter extends BasePresenter<WelfareModel, WelfareFragment> {

    @Override
    protected WelfareModel initModel() {
        return new WelfareModel();
    }

    public void getdata(final List<Results> results, int page, int size){
        getModel().getData(size,page,results)
                .compose(RxSchedulers.<List<Results>>FlowableToMain())
                .subscribe(new FlowableSubscriber<List<Results>>() {
                    private Subscription s;
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        //1， onSubscribe 是2.x新添加的方法，在发射数据前被调用，相当于1.x的onStart方法
                        //2， 参数为  Subscription ，Subscription 可用于向上游请求发射多少个元素，也可用于取笑请求
                        //3,  必须要调用Subscription 的request来请求发射数据，不然上游是不会发射数据的。
                        this.s = s;
                        this.s.request(100);
                    }

                    @Override
                    public void onNext(List<Results> data) {

                        if (data.size()<=0)
                            getView().mAdapter.loadMoreEnd();
                        else
                            getView().mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable t) {

                        getView().mAdapter.notifyDataSetChanged();
                        getView().mAdapter.loadMoreFail();//上拉加载

                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        getView().mAdapter.notifyDataSetChanged();
                    }
                });
    }



}
