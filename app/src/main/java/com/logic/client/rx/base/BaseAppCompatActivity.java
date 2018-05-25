package com.logic.client.rx.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.logic.client.R;
import com.logic.client.app.AppConstants;
import com.logic.client.rx.RxBus;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

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
        setTheme(R.style.DayTheme);
        mActivity = this;
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initBar();
        initView();
        initData();

        RxBus.getIntance().addSubscription(String.class, RxBus.getIntance().doSubscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                if (o.equals(AppConstants.SET_THEME)) {
                    setTheme(R.style.DayTheme);
                    Log.i("cesjo","DayTheme");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));

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
     * @param event ActivityEvent
     * @param <T>
     * @return
     */
    public <T> LifecycleTransformer<T> bindToLife(@NonNull ActivityEvent event) {
        return this.<T>bindUntilEvent(event);
    }

    /**
     * 入口
     *
     * @param ctx
     */
    public static void main(Context ctx, Class clas) {
        Intent intent = new Intent(ctx, clas);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    public static void main(Context ctx, Class clas, View view) {
        Intent intent = new Intent(ctx, clas);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) ctx, view, "新闻");
            ctx.startActivity(intent, options.toBundle());
        } else {

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) ctx, intent, options.toBundle());
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
