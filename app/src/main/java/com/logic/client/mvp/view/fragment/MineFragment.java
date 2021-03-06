package com.logic.client.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.app.AppConstants;
import com.logic.client.mvp.view.activity.ThemeActivity;
import com.logic.client.net.OkConstants;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.base.BaseFragment;

import org.polaric.colorful.ColorPickerDialog;
import org.polaric.colorful.Colorful;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/16
 * @desc
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    @BindView(R.id.tr_record)
    TableRow trRecord;
    @BindView(R.id.tr_feedback)
    TableRow trFeedback;
    @BindView(R.id.tr_theme)
    TableRow trTheme;
    @BindView(R.id.tr_setting)
    TableRow trSetting;
    @BindView(R.id.tr_about)
    TableRow trAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }
    boolean isNight = false ;
    @Override
    protected void initView() {

        trTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ceshi","trTheme");
                ThemeActivity.main(mActivity,ThemeActivity.class);
//                ColorPickerDialog dialog = new ColorPickerDialog(mActivity);
//                dialog.setOnColorSelectedListener(new ColorPickerDialog.OnColorSelectedListener() {
//                    @Override
//                    public void onColorSelected(Colorful.ThemeColor color) {
//                        Colorful.config(getContext()).primaryColor(color).apply();
//                        Colorful.config(getContext()).accentColor(color).apply();
//
//                    }
//                });
//                dialog.show();
            }
        });
    }

    @Override
    protected void initData() {

    }

}
