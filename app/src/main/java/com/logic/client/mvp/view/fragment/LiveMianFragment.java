package com.logic.client.mvp.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.adapter.BaseFragmentPagerAdapter;
import com.logic.client.bean.LiveChannelTabs;
import com.logic.client.mvp.presenter.LiveMianPresenter;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BaseFragment;
import com.logic.client.rx.base.mvp.BasePresenter;
import com.logic.client.widget.LgLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/23
 * @desc
 */

public class LiveMianFragment extends BaseFragment<LiveMianPresenter>  {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.vp_live)
    ViewPager vpLive;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<LiveFragment> mLiveFragments;
    private BaseFragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void initBar() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toTop();
            }
        });
        toolbar.setTitle(R.string.video);
        toolbar.setTitleTextColor(Color.WHITE);
        mPresenter.getLiveChannelTabs();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_mian;
    }

    @Override
    protected LiveMianPresenter initPresenter() {
        return new LiveMianPresenter() ;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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


    public void returnSelectChannel(List<LiveChannelTabs> liveChannelTabses) {
        if (liveChannelTabses == null)
            return;

        if (mLiveFragments == null)
            mLiveFragments = new ArrayList<>();

        int size = liveChannelTabses.size();
        mLiveFragments.clear();
        for (int i = 0; i <size ; i++) {
            LiveFragment newsFragment = new LiveFragment(liveChannelTabses.get(i).getName(),liveChannelTabses.get(i).getSlug());
            mLiveFragments.add(newsFragment);
        }

        if (mFragmentPagerAdapter == null) {
            mFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), mLiveFragments);
        } else
            mFragmentPagerAdapter.notifyDataSetChanged();

        vpLive.setAdapter(mFragmentPagerAdapter);
//        vpLive.setOffscreenPageLimit(size);
        tlTab.setupWithViewPager(vpLive);

        for (int i = 0; i < size; i++) {
            TabLayout.Tab tab = tlTab.getTabAt(i);
            tab.setText(liveChannelTabses.get(i).getName());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
