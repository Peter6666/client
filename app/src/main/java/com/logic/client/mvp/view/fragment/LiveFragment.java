package com.logic.client.mvp.view.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.logic.client.R;
import com.logic.client.adapter.LiveAdapter;
import com.logic.client.bean.LiveChannel;
import com.logic.client.mvp.presenter.LivePresenter;
import com.logic.client.mvp.view.activity.LiveRoomActivity;
import com.logic.client.rx.base.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/23
 * @desc //http://flv3.bn.netease.com/videolib3/1804/25/LvfCl5528/SD/LvfCl5528-mobile.mp4
 */

public class LiveFragment extends BaseFragment<LivePresenter> {

    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.iv_load)
    ImageView ivLoad;
    @BindView(R.id.tv_load)
    TextView tvLoad;
    @BindView(R.id.rcv_live)
    RecyclerView rcvLive;
    @BindView(R.id.srl_loadmore)
    SwipeRefreshLayout srlLoadmore;

    private String name;
    private String slug;
    private ConvenientBanner mBanner;
    private LiveChannel mLiveChannel;
    private List<LiveChannel.DataBean> mData;
    private List<LiveChannel.BigsquareBean> mBigsquare;
    private LiveAdapter mLiveAdapter;

    public LiveFragment() {


    }

    public LiveFragment(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public static LiveFragment newInstance(String name, String slug) {
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("slug", slug);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected LivePresenter initPresenter() {
        return new LivePresenter();
    }

    @Override
    protected void initView() {
        this.name  = getArguments().getString("name");
        this.slug= getArguments().getString("slug");

        mLiveChannel = new LiveChannel();
        mData = new ArrayList<LiveChannel.DataBean>();
        mBigsquare = new ArrayList<LiveChannel.BigsquareBean>();

        mLiveAdapter = new LiveAdapter(R.layout.rcv_live_item, mData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        rcvLive.setLayoutManager(gridLayoutManager);

//        getHeadView();
        rcvLive.addItemDecoration(new SpacesItemDecoration(5));
        rcvLive.setAdapter(mLiveAdapter);



        srlLoadmore.setColorSchemeResources(//颜色
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        srlLoadmore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.getQQnews(mData, name, mPage);
                srlLoadmore.setRefreshing(false);
            }
        });

        mLiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                LiveChannel.DataBean dataBean = mData.get(i);
                LiveRoomActivity.main(mActivity,dataBean.getUid());
            }
        });

    }

    public View getHeadView() {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.banner, null);
        mBanner = inflate.findViewById(R.id.banner);
        mLiveAdapter.addHeaderView(inflate);

        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder<LiveChannel.BigsquareBean> createHolder() {
                return new LocalImageHolderView();
            }
        }, mBigsquare)
                .setPageIndicator(new int[]{R.mipmap.ic_dot_normal, R.mipmap.ic_dot_pressed})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        if (!mBanner.isTurning()) {
            mBanner.startTurning(5000);
        }

        return mBanner;
    }

    @Override
    protected void initData() {
        mPresenter.getLive(slug);
    }

    public void ScrollToTop() {
        if (rcvLive != null)
            rcvLive.smoothScrollToPosition(0);
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


    public void returnLiveChannel(LiveChannel liveChannel) {

        if (liveChannel == null)
            return;
        List<LiveChannel.BigsquareBean> bigsquare = liveChannel.getBigsquare();
//        if (bigsquare != null && bigsquare.size() > 0 && mBanner!=null) {
//            mBanner.setVisibility(View.VISIBLE);
////            mBanner.getViewPager().getAdapter().notifyDataSetChanged();
////            mBanner.notifyDataSetChanged();
//        } else {
//            mBanner.setVisibility(View.GONE);
//        }
//        mBanner.getViewPager().getAdapter().notifyDataSetChanged();
        List<LiveChannel.DataBean> data = liveChannel.getData();
        if (data != null && data.size() > 0)
            mLiveAdapter.notifyDataSetChanged();
        mLiveAdapter.loadMoreComplete();
    }

    public void returnBanner(List<LiveChannel.BigsquareBean> bigsquare) {
        mBigsquare.addAll(bigsquare);
    }

    public void returnLiveData(List<LiveChannel.DataBean> data) {
        mData.addAll(data);
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    public class LocalImageHolderView implements Holder<LiveChannel.BigsquareBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int i, LiveChannel.BigsquareBean bigsquareBean) {
            String path = bigsquareBean.getThumb();
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.ic_default_img)
                    .error(R.mipmap.ic_default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBanner!=null && !mBanner.isTurning()) {
            mBanner.startTurning(4000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mBanner!=null){
            mBanner.stopTurning();
        }

    }

}
