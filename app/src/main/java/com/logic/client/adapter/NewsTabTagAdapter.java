package com.logic.client.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.logic.client.R;
import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.mvp.view.activity.NewsTabTagActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class NewsTabTagAdapter extends BaseQuickAdapter<NewsChannelTabs, BaseViewHolder>{

    private boolean isDelete;
    private final int ANIMTIME = 500;

    public NewsTabTagAdapter(@LayoutRes int layoutResId, @Nullable List<NewsChannelTabs> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsChannelTabs sData) {
        TextView tv_tagname = baseViewHolder.getView(R.id.tv_tagname);
        ImageView iv_tag_delete = baseViewHolder.getView(R.id.iv_tag_delete);

//        if (sData.getType()==NewsChannelTabs.CURRENT_CHANNEL){
        if (!sData.isFixed()){
            if (isDelete) {
                iv_tag_delete.setVisibility(View.VISIBLE);
                startScaleAnimation(iv_tag_delete);
            } else {
                if (iv_tag_delete.getVisibility() == View.VISIBLE) {
                    startAlphaAnimation(iv_tag_delete,1,0);
                    iv_tag_delete.setVisibility(View.GONE);
                }
            }
        }else
            iv_tag_delete.setVisibility(View.GONE);
//        }
        tv_tagname.setText(sData.getName());
    }


    private void startScaleAnimation(View v) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.3f, 1, 0.3f, 1);
        scaleAnimation.setDuration(ANIMTIME);
        scaleAnimation.setRepeatCount(0);
        v.startAnimation(scaleAnimation);
    }

    private void startAlphaAnimation(View v,float fromAlpha, float toAlpha) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(ANIMTIME);
        alphaAnimation.setRepeatCount(0);
        v.startAnimation(alphaAnimation);
    }

    public void removeItem(@IntRange(from = 0L) int position) {
        notifyItemRemoved(position);
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }


}
