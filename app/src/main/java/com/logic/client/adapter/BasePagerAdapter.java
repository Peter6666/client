package com.logic.client.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/25
 * @desc
 */

public class BasePagerAdapter extends PagerAdapter {

    private final Context ctx;
    private final List<View> mData;
//    public  int type=0;
//    private  ArrayList<WeakReference<View>> mViews;//缓存View


    public BasePagerAdapter(Context ctx, List<View> mData) {
        this.ctx = ctx;
        this.mData = mData;
    }

//    public BasePagerAdapter(Context ctx, List<View> data , int type) {
//        this.ctx = ctx;
//        mData = data;
//        mViews = new ArrayList<>();
//        this.type=type;
//    }

    @Override
    public int getCount() {
        return mData != null ? 0 : mData.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View t = mData.get(position);

//        if (type==1){
//            int size = mViews.size();
//            if (size>0){
//
//            }else {
//                container = new ImageView(ctx);
//            }
//        }

        container.addView(t);
        return t;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View t = mData.get(position);
//        mViews.add(new WeakReference<View>(t));
        container.removeView(t);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

}
