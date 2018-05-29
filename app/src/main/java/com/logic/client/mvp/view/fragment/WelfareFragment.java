package com.logic.client.mvp.view.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.logic.client.R;
import com.logic.client.adapter.WelfareAdapter;
import com.logic.client.bean.Results;
import com.logic.client.mvp.presenter.WelfarePresenter;
import com.logic.client.mvp.view.activity.NewsTabTagActivity;
import com.logic.client.mvp.view.activity.PictureActivity;
import com.logic.client.rx.base.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/27
 * @desc
 */

public class WelfareFragment extends BaseFragment<WelfarePresenter> implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcv_welfare)
    RecyclerView rcvWelfare;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private int mPage = 1;
    private int mSize = 10;
    private ArrayList<Results> mData;
    public WelfareAdapter mAdapter;



    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    protected void initBar() {
        toolbar.setTitle(R.string.welfare);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected WelfarePresenter initPresenter() {
        return new WelfarePresenter();
    }

    @Override
    protected void initView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,2);
        rcvWelfare.setLayoutManager(gridLayoutManager);
        fab.setOnClickListener(this);

        mData = new ArrayList<>();
        mAdapter = new WelfareAdapter(R.layout.rcv_welfare_item, mData);
        rcvWelfare.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {// 滑动最后一个Item的时候回调onLoadMoreRequested方法
            @Override
            public void onLoadMoreRequested() {
                mPage++;
                mPresenter.getdata(mData, mPage, mSize);
            }
        }, rcvWelfare);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ArrayList<Results> data = (ArrayList<Results>) baseQuickAdapter.getData();
                String url = data.get(i).getUrl();
                Intent intent = new Intent(mActivity,PictureActivity.class);
                intent.putExtra("imgUrl",url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {
        mPresenter.getdata( mData, mPage, mSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                rcvWelfare.smoothScrollToPosition(0);
                break;
        }
    }
}
