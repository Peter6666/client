package com.logic.client.rx.base.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.logic.client.bean.NewsChannelTabs;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements IView {

    public View rootView;
    public P mPresenter;
    public Unbinder bind;
    public FragmentActivity mActivity;
    protected boolean isViewCreated = false;
    protected boolean isLoad = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.setView(this);
            mPresenter.ctx = mActivity;
        }
        mActivity = getActivity();

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        bind = ButterKnife.bind(this, rootView);

        initBar();
        initView();
        isViewCreated = true;
        if (getUserVisibleHint()) {//可见 开始加载数据
            if (isViewCreated) {
                Log.i("ceshi","onCreateView initData");
                initData();
            }
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {//可见 开始加载数据
            if (isViewCreated) {
                Log.i("ceshi","setUserVisibleHint initData");
                initData();
            }
        } else {//不可见 停止加载数据

        }
    }

    protected abstract void initBar();


    public void startActivity(Class<?> clss) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clss);
        startActivity(intent);
    }

    /**
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    /**
     * @param event FragmentEvent
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife(@NonNull FragmentEvent event) {
        return this.<T>bindUntilEvent(event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null)
            bind.unbind();

        isViewCreated = false;
        isLoad = false;

    }

    protected abstract int getLayoutId();

    protected abstract P initPresenter();

    protected abstract void initView();

    protected abstract void initData();


}
