package com.logic.client.rx.base.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/16
 * @desc
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxActivity implements IView {

    public BaseActivity mActivity;
    public P mPresenter;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.setView(this);
            mPresenter.ctx = this;
        }

        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initView();
        initData();
    }

    public void startActivity(Class<?> clss) {
        Intent intent = new Intent();
        intent.setClass(this, clss);
        startActivity(intent);
    }

    ;

    /**
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    /**
     * @param event ActivityEvent
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife(@NonNull ActivityEvent event) {
        return this.<T>bindUntilEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    protected abstract P initPresenter();

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
