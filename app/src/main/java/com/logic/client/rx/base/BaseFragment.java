package com.logic.client.rx.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public abstract class BaseFragment extends RxFragment {

    private View rootView;
    public FragmentActivity mActivity;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        mActivity = getActivity();
        bind = ButterKnife.bind(this,rootView);
        initView();
        initData();
        return rootView;
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();


    /**
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    /**
     * @param  event FragmentEvent
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife(@NonNull FragmentEvent event) {
        return this.<T>bindUntilEvent(event);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind!=null)
            bind.unbind();
    }
}
