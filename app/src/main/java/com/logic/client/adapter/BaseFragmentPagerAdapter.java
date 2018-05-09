package com.logic.client.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/25
 * @desc
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mDate;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> date) {
        super(fm);
        mDate = date;
    }

    @Override
    public Fragment getItem(int i) {
        return mDate.get(i);
    }

    @Override
    public int getCount() {
        return mDate==null?0:mDate.size();
    }
}
