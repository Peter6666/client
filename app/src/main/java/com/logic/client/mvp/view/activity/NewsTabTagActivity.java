package com.logic.client.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.logic.client.R;
import com.logic.client.adapter.NewsTabTagAdapter;
import com.logic.client.app.AppConstants;
import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.mvp.presenter.NewsTabTagPresenter;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.base.mvp.BaseAppCompatActivity;
import com.logic.client.rx.base.mvp.BasePresenter;
import com.logic.client.utils.CacheUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class NewsTabTagActivity extends BaseAppCompatActivity<NewsTabTagPresenter> implements View.OnClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcv_news_tag)
    RecyclerView rcvNewsTag;
    @BindView(R.id.rcv_un_news_tag)
    RecyclerView rcvUnNewsTag;
    @BindView(R.id.tv_switching_channel)
    TextView tvSwitchingChannel;
    @BindView(R.id.tv_edit_channel)
    TextView tvEditChannel;
    @BindView(R.id.tv_add_channel)
    TextView tvAddChannel;
    public NewsTabTagAdapter newsTabTagAdapter;
    public NewsTabTagAdapter addNewsTabTagAdapter;
    private final String TAG = NewsTabTagActivity.class.getSimpleName();
    private ArrayList<NewsChannelTabs> mSelectChannelData;
    private ArrayList<NewsChannelTabs> mUnSelectChannelData;


    @Override
    protected void initBar() {
        setBar(toolbar,R.string.all_columns,R.mipmap.ic_back);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_tabtag;
    }

    @Override
    protected NewsTabTagPresenter initPresenter() {
        return new NewsTabTagPresenter();
    }

    @Override
    protected void initView() {


        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 4);
        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(mActivity, 4);
        rcvNewsTag.setLayoutManager(linearLayoutManager);
        rcvUnNewsTag.setLayoutManager(linearLayoutManager1);

    }

    @Override
    protected void initData() {
        mSelectChannelData = new ArrayList<>();
        mUnSelectChannelData = new ArrayList<>();
        mPresenter.getSelectChannel();
        mPresenter.getUnSelectChannel();
        newsTabTagAdapter = new NewsTabTagAdapter(R.layout.rcv_news_tag_item, mSelectChannelData);
        addNewsTabTagAdapter = new NewsTabTagAdapter(R.layout.rcv_news_tag_item, mUnSelectChannelData);
        rcvNewsTag.setAdapter(newsTabTagAdapter);
        rcvUnNewsTag.setAdapter(addNewsTabTagAdapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {//拖动
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = viewHolder1.getAdapterPosition();

                NewsChannelTabs fromTabs = mSelectChannelData.get(fromPosition);
                NewsChannelTabs toTabs = mSelectChannelData.get(toPosition);
                fromTabs.setIndex(toPosition);
                toTabs.setIndex(fromPosition);

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mSelectChannelData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mSelectChannelData, i, i - 1);
                    }
                }
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

                return true;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    TextView tv_tagname = viewHolder.itemView.findViewById(R.id.tv_tagname);
                    tv_tagname.setTextColor(mActivity.getResources().getColor(R.color.colorBlue));
                }
                super.onSelectedChanged(viewHolder, actionState);

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                TextView tv_tagname = viewHolder.itemView.findViewById(R.id.tv_tagname);
                tv_tagname.setTextColor(Color.BLACK);
                mPresenter.saveChannel(mSelectChannelData,null);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

            }
        });
        itemTouchHelper.attachToRecyclerView(rcvNewsTag);

        newsTabTagAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String s = tvEditChannel.getText().toString();
                Resources resources = mActivity.getResources();
                if (s.equals(resources.getString(R.string.fra_news_edit))) {
                    newsTabTagAdapter.setDelete(true);
                    tvEditChannel.setText(R.string.finish);
                    newsTabTagAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        tvEditChannel.setOnClickListener(this);

        addNewsTabTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View v, int i) {
                rcvUnNewsTagClick(baseQuickAdapter,v,i);
            }
        });
        newsTabTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                rcvNewsTagClick(baseQuickAdapter,view,i);
            }
        });
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

    public void returnSelectChannel(List<NewsChannelTabs> newsChannelTabses){
        mSelectChannelData.clear();
        mSelectChannelData.addAll(newsChannelTabses);
    }

    public void returnUnSelectChannel(List<NewsChannelTabs> newsChannelTabses){
        mUnSelectChannelData.clear();
        mUnSelectChannelData.addAll(newsChannelTabses);
    }

    private void tvEditChannelClick() {
        String s = tvEditChannel.getText().toString();
        Resources resources = mActivity.getResources();
        if (s.equals(resources.getString(R.string.fra_news_edit))) {
            newsTabTagAdapter.setDelete(true);
            tvEditChannel.setText(R.string.finish);
        } else if (s.equals(resources.getString(R.string.finish))) {
            newsTabTagAdapter.setDelete(false);
            tvEditChannel.setText(R.string.fra_news_edit);
        }
        newsTabTagAdapter.notifyDataSetChanged();
    }

    private void rcvNewsTagClick(BaseQuickAdapter baseQuickAdapter, View v, int i){

        NewsChannelTabs item = (NewsChannelTabs) baseQuickAdapter.getItem(i);
        String s = tvEditChannel.getText().toString();
        Resources resources = mActivity.getResources();
        if (!item.isFixed() && !s.equals(resources.getString(R.string.fra_news_edit))){
            int itemCount = addNewsTabTagAdapter.getItemCount();
            item.setSelect(false);
            addNewsTabTagAdapter.addData(itemCount,item);
            mSelectChannelData.remove(i);
            newsTabTagAdapter.removeItem(i);
            addNewsTabTagAdapter.notifyItemInserted(itemCount);

            mPresenter.saveChannel(mSelectChannelData,mUnSelectChannelData);
        }

    }

    private void rcvUnNewsTagClick(BaseQuickAdapter baseQuickAdapter, View v, int i){

        NewsChannelTabs item = (NewsChannelTabs) baseQuickAdapter.getItem(i);
        int itemCount = newsTabTagAdapter.getItemCount();
        item.setIndex(itemCount);
        item.setSelect(true);
        newsTabTagAdapter.addData(itemCount,item);
        mUnSelectChannelData.remove(i);
        baseQuickAdapter.notifyItemRemoved(i);
        newsTabTagAdapter.notifyItemInserted(itemCount);

        mPresenter.saveChannel(mSelectChannelData,mUnSelectChannelData);
    }

//    private void rcvNewsTagClick(View v){}


    @Override
    protected void onStop() {
        super.onStop();
        RxBus.getIntance().post(AppConstants.TAB);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit_channel:
                tvEditChannelClick();
                break;

        }
    }

}
