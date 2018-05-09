package com.logic.client.mvp.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.logic.client.R;
import com.logic.client.adapter.BaseFragmentPagerAdapter;
import com.logic.client.app.AppConstants;
import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.mvp.presenter.HomePresenter;
import com.logic.client.mvp.view.activity.NewsTabTagActivity;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.base.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/25
 * @desc
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements BaseQuickAdapter.UpFetchListener, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.vp_home)
    ViewPager vpHome;

    private List<Fragment> mNewsFragments;
    private BaseFragmentPagerAdapter mFragmentPagerAdapter;

    public HomeFragment(int i) {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        initBar();
        fab.setOnClickListener(this);
    }

    private void initBar() {

        toolbar.setTitle("首页");
        toolbar.setTitleTextColor(Color.WHITE);
        mPresenter.getSelectChannel();

//        new BasePagerAdapter(mActivity,)
//        vpHome.setAdapter();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("ceshi", "HomeFragment onSaveInstanceState");
        if (tlTab != null) {
            outState.putInt("HomeFragment_tab", tlTab.getSelectedTabPosition());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            int homeFragment_tab = savedInstanceState.getInt("HomeFragment_tab");
            Log.i("ceshi", "HomeFragment onCreateView" + homeFragment_tab);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initData() {

        mPresenter.backChannel();
        tvAdd.setOnClickListener(this);
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

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
    public void onUpFetch() {
//        mPage++;
//        mAdapter.setUpFetching(true);
//        mPresenter.getQQnews(rcvHome, mData, "头条", mPage);
//        if (mPage > 5)
//            mAdapter.setUpFetchEnable(false);//关闭自动加载
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mPresenter.toTop();
                break;

            case R.id.tv_add:
                NewsTabTagActivity.main(mActivity, NewsTabTagActivity.class);
                break;
        }
    }

    public void returnSelectChannel(List<NewsChannelTabs> newsChannelTabses) {
        if (newsChannelTabses == null)
            return;
        if (mNewsFragments == null)
            mNewsFragments = new ArrayList<>();

        int size = newsChannelTabses.size();
        mNewsFragments.clear();
        for (int i = 0; i < size; i++) {
            NewsFragment newsFragment = new NewsFragment(newsChannelTabses.get(i).getName());
            mNewsFragments.add(newsFragment);
        }

        if (mFragmentPagerAdapter == null) {
            mFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), mNewsFragments);
        } else
            mFragmentPagerAdapter.notifyDataSetChanged();

        vpHome.setAdapter(mFragmentPagerAdapter);
        vpHome.setOffscreenPageLimit(size);
        tlTab.setupWithViewPager(vpHome);

        for (int i = 0; i < size; i++) {
            TabLayout.Tab tab = tlTab.getTabAt(i);
            tab.setText(newsChannelTabses.get(i).getName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
