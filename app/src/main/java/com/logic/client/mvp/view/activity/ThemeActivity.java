package com.logic.client.mvp.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.logic.client.R;
import com.logic.client.rx.base.BaseActivity;
import com.logic.client.rx.base.BaseApplication;

import org.polaric.colorful.ColorPickerDialog;
import org.polaric.colorful.Colorful;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/28
 * @desc
 */

public class ThemeActivity extends BaseActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.primary_color_img)
    AppCompatImageView primaryColorImg;
    @BindView(R.id.accent_color_text)
    AppCompatImageView accentColorText;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sw)
    Switch sw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_theme;
    }

    @Override
    protected void initView() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        boolean night = Colorful.getThemeDelegate().isNight();
        if (night){
            sw.setChecked(true);
            toolbar.setTitle("夜间模式");
        }
        else{
            sw.setChecked(false);
            toolbar.setTitle("日间模式");
        }

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                sw.setChecked(isChecked);
//                if (isChecked){
//                    toolbar.setTitle("夜间模式");
//                }
//                else{
//                    toolbar.setTitle("日间模式");
//                }
//                Colorful.config(mActivity).night(isChecked).apply();
            }
        });

        fab.setBackgroundTintList(createColorStateList(Colorful.getThemeDelegate().getPrimaryColor().getColorRes()));
        fab.setImageDrawable(tintDrawableWithColor(R.drawable.svg_ic_windmill,
                Colorful.getThemeDelegate().getAccentColor().getColorRes()));
        primaryColorImg.setImageDrawable(tintDrawableWithColor(R.drawable.svg_ic_suit,
                Colorful.getThemeDelegate().getPrimaryColor().getColorRes()));
        accentColorText.setImageDrawable(tintDrawableWithColor(R.drawable.svg_ic_tie,
                Colorful.getThemeDelegate().getAccentColor().getColorRes()));

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.primary_color_img, R.id.accent_color_text})
    public void onViewClicked(View view) {
        ColorPickerDialog dialog = new ColorPickerDialog(mActivity);
        switch (view.getId()) {
            case R.id.primary_color_img:

                dialog.setOnColorSelectedListener(new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(Colorful.ThemeColor color) {
                        Colorful.config(mActivity).primaryColor(color).apply();

                        toolbarLayout.setContentScrimColor(ContextCompat.getColor(mActivity, color.getColorRes()));
                        fab.setBackgroundTintList(createColorStateList(color.getColorRes()));
                        primaryColorImg.setImageDrawable(tintDrawableWithColor(R.drawable.svg_ic_suit,
                                color.getColorRes()));
                        playFabAnimation();
                    }
                });


                break;
            case R.id.accent_color_text:

                dialog.setOnColorSelectedListener(new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(Colorful.ThemeColor color) {
                        Colorful.config(mActivity).accentColor(color).apply();

                        fab.setImageDrawable(tintDrawableWithColor(R.drawable.svg_ic_windmill,
                                color.getColorRes()));
                        accentColorText.setImageDrawable(tintDrawableWithColor(R.drawable.svg_ic_tie,
                                color.getColorRes()));

                        playFabAnimation();
                    }
                });

                break;
        }
        dialog.show();
    }

    //修改背景
    protected Drawable tintDrawableWithColor(@DrawableRes int drawableRes, @ColorRes int colorRes) {
        Drawable drawable = ContextCompat.getDrawable(mActivity, drawableRes);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(mActivity, colorRes));
        return drawable;
    }

    protected ColorStateList createColorStateList(@ColorRes int colorRes) {
        int color = ContextCompat.getColor(mActivity, colorRes);
        int[] colors = new int[]{color, color, color, color, color, color};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

    private void playFabAnimation() {
//        fab.setScaleX(0f);
//        fab.setScaleY(0f);
//        fab.setAlpha(0f);

        fab.animate().cancel();
        fab.animate()
                .rotation(360 * 2)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fab.animate().cancel();
                    }
                });
    }

}
