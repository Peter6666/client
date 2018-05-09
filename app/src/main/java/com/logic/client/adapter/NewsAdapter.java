package com.logic.client.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.logic.client.R;
import com.logic.client.bean.GankData;
import com.logic.client.bean.IdataNews;
import com.logic.client.bean.Results;
import com.logic.client.mvp.view.activity.PictureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/26
 * @desc
 */

public class NewsAdapter extends BaseMultiItemQuickAdapter<IdataNews.Idate, BaseViewHolder> {

    public NewsAdapter(@Nullable List<IdataNews.Idate> data) {
        super(data);
        addItemType(IdataNews.PIC_NO, R.layout.rcv_home_item);
        addItemType(IdataNews.PIC_ONLY, R.layout.rcv_home_item);
        addItemType(IdataNews.PIC_MULTI, R.layout.rcv_home_item);
    }

//    public NewsAdapter(@LayoutRes int layoutResId, @Nullable List<IdataNews.Idate> data) {
//        super(layoutResId, data);
//        addItemType(IdataNews.PIC_NO, R.layout.rcv_home_item);
//        addItemType(IdataNews.PIC_NO, R.layout.rcv_home_item);
//    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, IdataNews.Idate idate) {

        switch (baseViewHolder.getItemViewType()) {

            case IdataNews.PIC_NO:
                doPicNo(baseViewHolder, idate);
                break;

            case IdataNews.PIC_ONLY:
                doPicOnly(baseViewHolder, idate);
                break;

            case IdataNews.PIC_MULTI:
                doPicMulti(baseViewHolder, idate);
                break;
        }
    }

    private void doPicMulti(BaseViewHolder baseViewHolder, final IdataNews.Idate idate) {

        View view = baseViewHolder.getView(R.id.cv_pic_mult);
        view.setVisibility(View.VISIBLE);
        TextView tv_rvc_home_title = baseViewHolder.getView(R.id.tv_rvc_home_title);
        TextView tv_rvc_home_content = baseViewHolder.getView(R.id.tv_rvc_home_content);
        TextView tv_rvc_home_time = baseViewHolder.getView(R.id.tv_rvc_home_time);
        TextView tv_rvc_home_desc = baseViewHolder.getView(R.id.tv_rvc_home_desc);
        final String title = idate.getTitle();
        if (!title.isEmpty()) {
            tv_rvc_home_title.setText(title);
        }

        if (!idate.getContent().isEmpty()) {
            tv_rvc_home_content.setText(idate.getContent());
        } else
            tv_rvc_home_content.setVisibility(View.GONE);

        if (!idate.getPublishDateStr().isEmpty()) {
            tv_rvc_home_time.setText("时间: " + idate.getPublishDateStr());
        } else
            tv_rvc_home_time.setVisibility(View.GONE);

        if (!idate.getPosterScreenName().isEmpty()) {
            tv_rvc_home_desc.setText("来源: " + idate.getPosterScreenName());
        } else
            tv_rvc_home_desc.setVisibility(View.GONE);

        RecyclerView iv_rcv_home_img = baseViewHolder.getView(R.id.rv_rvc_home_img);
        final ArrayList<String> imageUrls = idate.getImageUrls();
        int size = imageUrls.size();
        iv_rcv_home_img.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager;
        if (size == 2) {
            gridLayoutManager = new GridLayoutManager(mContext, 2);

        } else {
            gridLayoutManager = new GridLayoutManager(mContext, 3);
        }
        iv_rcv_home_img.setLayoutManager(gridLayoutManager);
        GridAdapter gridAdapter = new GridAdapter(R.layout.rvc_home_gridview_item, imageUrls);
        iv_rcv_home_img.setAdapter(gridAdapter);
        gridAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(mContext,PictureActivity.class);
                intent.putStringArrayListExtra("imgUrl",imageUrls);
                intent.putExtra("title",title);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        iv_rcv_home_img.setFocusableInTouchMode(false);
        iv_rcv_home_img.requestFocus();

    }

    private void doPicOnly(BaseViewHolder baseViewHolder, IdataNews.Idate idate) {
        View view = baseViewHolder.getView(R.id.cv_pic_only);
        view.setVisibility(View.VISIBLE);

        TextView tv_rvc_home_title = baseViewHolder.getView(R.id.tv_rvc_only_title);
        TextView tv_rvc_home_content = baseViewHolder.getView(R.id.tv_rvc_only_content);
        TextView tv_rvc_home_time = baseViewHolder.getView(R.id.tv_rvc_only_time);
        TextView tv_rvc_home_desc = baseViewHolder.getView(R.id.tv_rvc_only_desc);

        if (!idate.getTitle().isEmpty()) {
            tv_rvc_home_title.setText(idate.getTitle());
        }

        if (!idate.getContent().isEmpty()) {
            tv_rvc_home_content.setText(idate.getContent());
        } else
            tv_rvc_home_content.setVisibility(View.GONE);

        if (!idate.getPublishDateStr().isEmpty()) {
            tv_rvc_home_time.setText("时间: " + idate.getPublishDateStr());
        } else
            tv_rvc_home_time.setVisibility(View.GONE);

        if (!idate.getPosterScreenName().isEmpty()) {
            tv_rvc_home_desc.setText("来源: " + idate.getPosterScreenName());
        } else
            tv_rvc_home_desc.setVisibility(View.GONE);
        List<String> imageUrls = idate.getImageUrls();
        ImageView iv_gv_img = baseViewHolder.getView(R.id.iv_noly);
        Glide.with(mContext)
                .load(imageUrls.get(0))
                .placeholder(R.mipmap.ic_default_img)
                .error(R.mipmap.ic_default_img)
                .into(iv_gv_img);

    }

    private void doPicNo(BaseViewHolder baseViewHolder, IdataNews.Idate idate) {
        View view = baseViewHolder.getView(R.id.cv_pic_no);
        view.setVisibility(View.VISIBLE);
        TextView tv_rvc_home_title = baseViewHolder.getView(R.id.tv_rvc_no_title);
        TextView tv_rvc_home_content = baseViewHolder.getView(R.id.tv_rvc_no_content);
        TextView tv_rvc_home_time = baseViewHolder.getView(R.id.tv_rvc_no_time);
        TextView tv_rvc_home_desc = baseViewHolder.getView(R.id.tv_rvc_no_desc);

        if (!idate.getTitle().isEmpty()) {
            tv_rvc_home_title.setText(idate.getTitle());
        }

        if (!idate.getContent().isEmpty()) {
            tv_rvc_home_content.setText(idate.getContent());
        } else
            tv_rvc_home_content.setVisibility(View.GONE);

        if (!idate.getPublishDateStr().isEmpty()) {
            tv_rvc_home_time.setText("时间: " + idate.getPublishDateStr());
        } else
            tv_rvc_home_time.setVisibility(View.GONE);

        if (!idate.getPosterScreenName().isEmpty()) {
            tv_rvc_home_desc.setText("来源: " + idate.getPosterScreenName());
        } else
            tv_rvc_home_desc.setVisibility(View.GONE);

    }

}
