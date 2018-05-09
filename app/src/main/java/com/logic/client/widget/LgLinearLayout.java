package com.logic.client.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.rx.base.BaseApplication;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/7
 * @desc
 */

public class LgLinearLayout extends LinearLayout{

    private ProgressBar pb_load;
    private ImageView iv_load;
    private TextView tv_load;

    public LgLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    public LgLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public LgLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LgLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = View.inflate(context, R.layout.loadding, this);
        tv_load = findViewById(R.id.tv_load);
        iv_load = findViewById(R.id.iv_load);
        pb_load = findViewById(R.id.pb_load);
        tv_load.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (STATUS_CURRENT == STATUS_FAIL ) {
                    if (onReloadListener != null) {
                        onReloadListener.OnReload();
                    }
//                }
            }
        });
        setVisibility(View.GONE);
    }

    public static  int STATUS_CURRENT = 0;
    public static final int STATUS_LOAD = 1;
    public static final int STATUS_FAIL = 2;
    public static final int STATUS_END = 3;


    public void setLoadStatus(int status) {
        Log.i("ceshi","status "+status);
        switch (status){
            case STATUS_LOAD:
                setVisibility(View.VISIBLE);
                STATUS_CURRENT=STATUS_LOAD;
                tv_load.setVisibility(VISIBLE);
                iv_load.setVisibility(GONE);
                pb_load.setVisibility(VISIBLE);
                tv_load.setText(BaseApplication.getResource().getString(R.string.loadding));
                break;

            case STATUS_FAIL:
                setVisibility(View.VISIBLE);
                STATUS_CURRENT=STATUS_FAIL;
                tv_load.setVisibility(VISIBLE);
                iv_load.setVisibility(VISIBLE);
                pb_load.setVisibility(GONE);
                tv_load.setText(BaseApplication.getResource().getString(R.string.load_fail));
                break;

            case STATUS_END:
                STATUS_CURRENT=STATUS_END;
                setVisibility(View.GONE);
                break;
        }
    }

   private onReloadListener onReloadListener;
    public void setOnReloadListener(onReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface onReloadListener {
        void OnReload();
    }


}
