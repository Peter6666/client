package com.logic.client.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.mvp.presenter.NewsMianPresenter;
import com.logic.client.rx.base.mvp.BaseActivity;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public class HomeActivity extends BaseActivity<NewsMianPresenter> {

    private TextView viewById;

    @Override
    protected NewsMianPresenter initPresenter() {
        return new NewsMianPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    public static void main(Context ctx){
        Intent intent = new Intent(ctx, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    @Override
    protected void initView() {
        viewById = findViewById(R.id.textView);
    }

    @Override
    protected void initData() {

    }
}
