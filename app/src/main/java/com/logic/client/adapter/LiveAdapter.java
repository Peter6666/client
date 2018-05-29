package com.logic.client.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.logic.client.R;
import com.logic.client.bean.LiveChannel;

import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/23
 * @desc
 */

public class LiveAdapter extends BaseQuickAdapter<LiveChannel.DataBean, BaseViewHolder> {

    public LiveAdapter(@LayoutRes int layoutResId, @Nullable List<LiveChannel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LiveChannel.DataBean dataBean) {

        ImageView iv_img = baseViewHolder.getView(R.id.iv_img);
        TextView tvStatus = baseViewHolder.getView(R.id.tvStatus);
        TextView tvTitle = baseViewHolder.getView(R.id.tvTitle);
        TextView tvName = baseViewHolder.getView(R.id.tvName);
        TextView tvViewer = baseViewHolder.getView(R.id.tvViewer);

        String title = dataBean.getTitle();
        if (isNotEmpty(title)){
            tvTitle.setText(title);
        }

        String view = dataBean.getView();
        if (isNotEmpty(view)){
            tvViewer.setText(view);
        }

        String getNick = dataBean.getNick();
        if (isNotEmpty(getNick)){
            tvName.setText(getNick);
        }

//        String status = dataBean.getStatus();
//        if (isNotEmpty(status)){
//            if (status.equals("2"))
//                tvStatus.setVisibility(View.VISIBLE);
//            else
//                tvStatus.setVisibility(View.GONE);
//        }

        String live_thumb = dataBean.getLive_thumb();
        Glide.with(mContext)
                .load(live_thumb)
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_img);
    }

    public boolean isNotEmpty(String val) {

        if (val != null && !val.isEmpty())
            return true;
        return false;
    }
}
