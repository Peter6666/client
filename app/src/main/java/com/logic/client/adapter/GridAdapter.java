package com.logic.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.logic.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class GridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GridAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        ImageView iv_gv_img = baseViewHolder.getView(R.id.iv_gv_img);
        Glide.with(mContext)
                .load(s)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .into(iv_gv_img);
    }
}
