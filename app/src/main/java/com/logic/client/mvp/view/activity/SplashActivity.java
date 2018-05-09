package com.logic.client.mvp.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.logic.client.R;
import com.logic.client.bean.GankData;
import com.logic.client.bean.Results;
import com.logic.client.mvp.MainActivity;
import com.logic.client.net.OkClient;
import com.logic.client.rx.RxHelper;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.mvp.BaseActivity;
import com.logic.client.rx.base.mvp.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Random;

import io.reactivex.BackpressureStrategy;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/13
 */

public class SplashActivity extends BaseActivity {

    public final static String TAG = SplashActivity.class.getSimpleName();
    private TextView tv_time;
    private ImageView iv_sp;
    private String IMG_URL = "http://old.bz55.com/uploads/allimg/130725/1-130H5105020.jpg";

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        iv_sp = findViewById(R.id.iv_sp);
        tv_time = findViewById(R.id.tv_time);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSkip();
            }
        });
    }

    @Override
    protected void initData() {
        Glide.with(this)
                .load(IMG_URL)
                .placeholder(R.mipmap.ic_launcher)
                .into(iv_sp);

//        OkClient.getDefault(1).getWelfareList(10, new Random(System.currentTimeMillis()).nextInt(20))
//                .toFlowable(BackpressureStrategy.BUFFER)
//                .compose(RxSchedulers.<GankData>FlowableToMain())
//                .subscribe(new FlowableSubscriber<GankData>() {
//
//                    private Subscription s;
//
//                    @Override
//                    public void onSubscribe(@NonNull Subscription s) {
//                        this.s = s;
//                        s.request(Long.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onNext(GankData gankData) {
//                        List<Results> results1 = gankData.getResults();
//                        if (results1.size()>0){
//                            Results results = gankData.getResults().get(new Random(System.currentTimeMillis()).nextInt(results1.size()-1));
//                            String url = results.getUrl();
//                            Glide.with(mActivity)
//                                    .load(url)
//                                    .placeholder(R.mipmap.ic_launcher)
//                                    .into(iv_sp);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        t.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        RxHelper.countdown(3)
                .compose(this.<Integer>bindToLifecycle())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        tv_time.setText("跳过 " + integer);
                        if (integer == 0) {
                            doSkip();
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Log.e(TAG, throwable.toString());
                        doSkip();
                    }
                });
    }


    private void doSkip() {
        finish();
        MainActivity.main(mActivity, MainActivity.class);
    }
}
