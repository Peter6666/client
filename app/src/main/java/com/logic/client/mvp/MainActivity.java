package com.logic.client.mvp;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.adapter.BaseFragmentPagerAdapter;
import com.logic.client.mvp.view.fragment.HomeFragment;
import com.logic.client.mvp.view.fragment.WelfareFragment;
import com.logic.client.rx.base.BaseActivity;
import com.logic.client.rx.base.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/24
 * @desc
 */

public class MainActivity extends BaseAppCompatActivity implements TabLayout.OnTabSelectedListener {



    @BindView(R.id.vp_content_main)
    ViewPager vpContentMain;
    @BindView(R.id.ty_bottom_tab)
    TabLayout tyBottomTab;

    private String[] mTabs = {"首页", "福利", "视频", "我的"};

    private int[] mUnSelectTab = {R.mipmap.icon_tabbar_subscription, R.mipmap.icon_tabbar_notification,
            R.mipmap.icon_tabbar_home, R.mipmap.icon_tabbar_me};
    private int[] mSelectTab = {R.mipmap.icon_tabbar_subscription_active, R.mipmap.icon_tabbar_notification_active,
            R.mipmap.icon_tabbar_home_active, R.mipmap.icon_tabbar_me_active};
    private ArrayList<View> mTabViews;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        HomeFragment fragment = new HomeFragment(1);
        WelfareFragment welfareFragment = new WelfareFragment();
        WelfareFragment fragment2 = new WelfareFragment();
        WelfareFragment fragment3 = new WelfareFragment();
        fragments.add(fragment);
        fragments.add(welfareFragment);
        fragments.add(fragment2);
        fragments.add(fragment3);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vpContentMain.setAdapter(baseFragmentPagerAdapter);
        vpContentMain.setOffscreenPageLimit(4);
        tyBottomTab.setupWithViewPager(vpContentMain);

        tyBottomTab.addOnTabSelectedListener(this);
//        tyBottomTab.setTabTextColors(Color.BLACK, mActivity.getResources().getColor(R.color.colorBlue)); //设置标签的字体颜色，1为未选中标签的字体颜色，2为被选中标签的字体颜色
        mTabViews = new ArrayList<>();
        for (int i = 0; i < mTabs.length; i++) {
            TabLayout.Tab tab = tyBottomTab.getTabAt(i);
            tab.setCustomView(getTabView(tab, i, mTabViews));
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initBar() {

    }

    public View getTabView(TabLayout.Tab tab, int pos, ArrayList<View> mTabViews) {
        boolean selected = tab.isSelected();
        View inflate = LayoutInflater.from(this).inflate(R.layout.main_bottom_tab, null);
        ImageView iv_tab = inflate.findViewById(R.id.iv_tab);
        TextView tv_tab = inflate.findViewById(R.id.tv_tab);
        mTabViews.add(tv_tab);
        mTabViews.add(iv_tab);
        tv_tab.setText(mTabs[pos]);
        if (selected) {
            iv_tab.setImageResource(mSelectTab[pos]);
            tv_tab.setTextColor(mActivity.getResources().getColor(R.color.colorClickDark));
        } else
            iv_tab.setImageResource(mUnSelectTab[pos]);

        return inflate;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        Resources resources = mActivity.getResources();
        TextView tv = (TextView) mTabViews.get(position * 2);
        ImageView iv = (ImageView) mTabViews.get(position * 2 + 1);
        tv.setTextColor(resources.getColor(R.color.colorClickDark));
        iv.setImageResource(mSelectTab[position]);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        int position = tab.getPosition();
        Resources resources = mActivity.getResources();
        TextView tv = (TextView) mTabViews.get(position * 2);
        ImageView iv = (ImageView) mTabViews.get(position * 2 + 1);
        iv.setImageResource(mUnSelectTab[position]);
        tv.setTextColor(resources.getColor(R.color.colorDark));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
