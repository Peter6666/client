package com.logic.client.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.logic.client.R;
import com.logic.client.rx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/8
 * @desc
 */

public class PhotoViewFragment extends BaseFragment {
    private final String img;
    @BindView(R.id.pv_pic)
    PhotoView pvPic;

    public PhotoViewFragment(String img) {
        this.img = img;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photoview;
    }

    @Override
    protected void initView() {

        Glide.with(mActivity)
                .load(img)
                .into(pvPic);
    }

    @Override
    protected void initData() {

    }


}
