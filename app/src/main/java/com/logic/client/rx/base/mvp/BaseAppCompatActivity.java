package com.logic.client.rx.base.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.logic.client.R;
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

public abstract class BaseAppCompatActivity<P extends BasePresenter> extends RxAppCompatActivity implements IView {

    public BaseAppCompatActivity mActivity;
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
        initBar();
        initView();
        initData();
    }


    public void startActivity(Class<?> clss) {
        Intent intent = new Intent();
        intent.setClass(this, clss);
        startActivity(intent);
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

    public void setBar(Toolbar toolbar, int all_columns, int ic_back) {
        toolbar.setTitle(all_columns);
        toolbar.setNavigationIcon(ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract void initBar();

    protected abstract int getLayoutId();

    protected abstract P initPresenter();

    protected abstract void initView();

    protected abstract void initData();
}
