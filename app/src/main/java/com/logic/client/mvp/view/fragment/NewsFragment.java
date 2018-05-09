package com.logic.client.mvp.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.logic.client.R;
import com.logic.client.adapter.NewsAdapter;
import com.logic.client.app.AppConstants;
import com.logic.client.bean.IdataNews;
import com.logic.client.mvp.presenter.NewsPresenter;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BaseFragment;
import com.logic.client.widget.LgLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class NewsFragment extends BaseFragment<NewsPresenter> implements LgLinearLayout.onReloadListener {

    @BindView(R.id.rcv_home)
    RecyclerView rcvHome;
    @BindView(R.id.srl_loadmore)
    SwipeRefreshLayout srl_loadmore;
    @BindView(R.id.ly_loadding)
    View ly_loadding;
    @BindView(R.id.pb_load)
    ProgressBar pb_load;
    @BindView(R.id.iv_load)
    ImageView iv_load;
    @BindView(R.id.tv_load)
    TextView tv_load;

    private String name;
    public NewsAdapter mAdapter;
    private int mPage = 1;
    private int mSize = 10;
    private ArrayList<IdataNews.Idate> mData;
    public static int STATUS_CURRENT = 0;

    public NewsFragment(String name) {
        this.name = name;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected NewsPresenter initPresenter() {
        return new NewsPresenter();
    }

    @Override
    protected void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rcvHome.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {

        mData = new ArrayList<>();
        mPresenter.getQQnews(mData, name, mPage);
        mAdapter = new NewsAdapter(mData);
        rcvHome.setAdapter(mAdapter);
//        mAdapter.setUpFetchEnable(true);//自动加载
//        mAdapter.setUpFetchListener(this);
//        mAdapter.setStartUpFetchPosition(mSize);
//        mAdapter.disableLoadMoreIfNotFullPage();//第一次不进回调
//        mAdapter.setLoadMoreView(new BaseLoadMoreView());//设置自定义加载布局
//        mAdapter.setPreLoadNumber(int); // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {// 滑动最后一个Item的时候回调onLoadMoreRequested方法
            @Override
            public void onLoadMoreRequested() {
                mPage++;
                mPresenter.getQQnews(mData, name, mPage);
            }
        }, rcvHome);
//        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        ly_loadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoadStatus(LgLinearLayout.STATUS_LOAD);
                mPresenter.getQQnews(mData, name, mPage);
            }
        });

        srl_loadmore.setColorSchemeResources(//颜色
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        srl_loadmore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getQQnews(mData, name, mPage);
            }
        });
    }


    public void ScrollToTop() {
        if (rcvHome != null)
            rcvHome.smoothScrollToPosition(0);
    }

    public void setLoadStatus(int status) {
        switch (status) {
            case LgLinearLayout.STATUS_LOAD:
                ly_loadding.setVisibility(View.VISIBLE);
                STATUS_CURRENT = LgLinearLayout.STATUS_LOAD;
                tv_load.setVisibility(View.VISIBLE);
                iv_load.setVisibility(View.GONE);
                pb_load.setVisibility(View.VISIBLE);
                tv_load.setText(BaseApplication.getResource().getString(R.string.loadding));
                break;

            case LgLinearLayout.STATUS_FAIL:
                ly_loadding.setVisibility(View.VISIBLE);
                STATUS_CURRENT = LgLinearLayout.STATUS_FAIL;
                tv_load.setVisibility(View.VISIBLE);
                iv_load.setVisibility(View.VISIBLE);
                pb_load.setVisibility(View.GONE);
                tv_load.setText(BaseApplication.getResource().getString(R.string.load_fail));
                break;

            case LgLinearLayout.STATUS_END:
                STATUS_CURRENT = LgLinearLayout.STATUS_END;
                ly_loadding.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("ceshi", "onSaveInstanceState==" + name);
    }


    public void returnData(List<IdataNews.Idate> idates) {

        if (idates.size() <= 0)
            mAdapter.loadMoreEnd();
        else
            mAdapter.loadMoreComplete();
    }

    int mErr = 0;

    @Override
    public void showError(String msg) {

        if (mErr < 3) {
            mErr++;
            mPresenter.getQQnews(mData, name, mPage);
            return;
        } else {
            mErr = 0;
            srl_loadmore.setRefreshing(false);
            if (mPage == 1)
                setLoadStatus(LgLinearLayout.STATUS_FAIL);
        }

        mAdapter.loadMoreFail();
//        Log.i("ceshi","showError"+name +msg);
    }

    @Override
    public void showLoading(String title) {

        if (mPage == 1 && !srl_loadmore.isRefreshing())
            setLoadStatus(LgLinearLayout.STATUS_LOAD);
    }

    @Override
    public void stopLoading() {
        if (mPage == 1)
            setLoadStatus(LgLinearLayout.STATUS_END);
        mAdapter.notifyDataSetChanged();
        srl_loadmore.setRefreshing(false);
//        Log.i("ceshi","stopLoading"+name);
    }

    @Override
    public void OnReload() {
//        if (lly!=null){
        setLoadStatus(LgLinearLayout.STATUS_LOAD);
        mPresenter.getQQnews(mData, name, mPage);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
