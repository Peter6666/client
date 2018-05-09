package com.logic.client.mvp.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.logic.client.R;
import com.logic.client.adapter.BaseFragmentPagerAdapter;
import com.logic.client.adapter.BasePagerAdapter;
import com.logic.client.adapter.GridAdapter;
import com.logic.client.mvp.view.fragment.PhotoViewFragment;
import com.logic.client.rx.base.BaseAppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/8
 * @desc
 */

public class PictureActivity extends BaseAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_pic)
    ViewPager vp_pic;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private ArrayList<String> imgUrl;
    private String title;
    private GridAdapter mAdapter;
    private ArrayList<Fragment> photoViewFragments;
    private int size;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        imgUrl = intent.getStringArrayListExtra("imgUrl");
        title = intent.getStringExtra("title");
        size = imgUrl.size();
    }

    @Override
    protected void initData() {
        photoViewFragments = new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            PhotoViewFragment photoViewFragment = new PhotoViewFragment(imgUrl.get(i));
            photoViewFragments.add(photoViewFragment);
        }
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), photoViewFragments);
        vp_pic.setAdapter(baseFragmentPagerAdapter);

        vp_pic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                i++;
                tvContent.setText(i+"/"+size+"  "+title);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initBar() {
        toolbar.setTitle(R.string.image_see);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
