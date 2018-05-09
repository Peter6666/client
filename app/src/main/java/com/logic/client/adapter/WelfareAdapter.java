package com.logic.client.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.logic.client.R;
import com.logic.client.bean.Results;

import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/27
 * @desc
 */

public class WelfareAdapter extends BaseQuickAdapter<Results,BaseViewHolder> {

    public WelfareAdapter(@LayoutRes int layoutResId, @Nullable List<Results> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Results itemData) {
        ImageView iv_rcv_home_img = baseViewHolder.getView(R.id.iv_rcv_home_img);
        Glide.with(mContext)
                .load(itemData.getUrl())
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .into(iv_rcv_home_img);
    }
}
