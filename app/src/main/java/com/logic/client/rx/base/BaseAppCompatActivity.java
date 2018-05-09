package com.logic.client.rx.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public abstract class BaseAppCompatActivity extends RxAppCompatActivity {

    public BaseAppCompatActivity mActivity;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initBar();
        initView();
        initData();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initBar();

    /**
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    /**
     * @param  event  ActivityEvent
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife(@NonNull ActivityEvent event) {
        return this.<T>bindUntilEvent(event);
    }

    /**
     * 入口
     * @param ctx
     */
    public static void main(Context ctx,Class clas) {
        Intent intent = new Intent(ctx, clas);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
