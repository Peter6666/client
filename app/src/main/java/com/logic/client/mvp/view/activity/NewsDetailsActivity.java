package com.logic.client.mvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.dialog.LgAlertDialog;
import com.logic.client.rx.base.BaseActivity;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.widget.LgLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/16
 * @desc
 */

public class NewsDetailsActivity extends BaseActivity {


    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wv_news)
    WebView wvNews;
    @BindView(R.id.ly_loadding)
    View ly_loadding;
    @BindView(R.id.pb_load)
    ProgressBar pb_load;
    @BindView(R.id.iv_load)
    ImageView iv_load;
    @BindView(R.id.tv_load)
    TextView tv_load;

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        url = mActivity.getIntent().getStringExtra("url");
        ly_loadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoadStatus(LgLinearLayout.STATUS_LOAD);
                wvNews.loadUrl(url);
            }
        });
        initWebView();
    }

    @Override
    protected void initData() {

    }

    private void initWebView() {
        WebSettings settings = wvNews.getSettings();
        settings.setJavaScriptEnabled(true);
        wvNews.setWebViewClient(new ExampleWebViewClient());
        wvNews.loadUrl(url);


    }

    private class ExampleWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            setLoadStatus(LgLinearLayout.STATUS_FAIL);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;//给当前应用一个机会来异步处理按键事件。返回true，WebView将不会处理该按键事件，返回false，WebView将处理该按键事件。默认返回是false。
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setLoadStatus(LgLinearLayout.STATUS_END);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setLoadStatus(LgLinearLayout.STATUS_LOAD);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }

    public static int STATUS_CURRENT = 0;

    public void setLoadStatus(int status) {
        if (ly_loadding==null)
        return;
        switch (status) {
            case LgLinearLayout.STATUS_LOAD:
                ly_loadding.setVisibility(View.VISIBLE);
                STATUS_CURRENT = LgLinearLayout.STATUS_LOAD;
                tv_load.setVisibility(View.VISIBLE);
                iv_load.setVisibility(View.GONE);
                pb_load.setVisibility(View.VISIBLE);
                tv_load.setText(BaseApplication.getResource().getString(R.string.loadding));
                break;

            case LgLinearLayout.STATUS_FAIL:
                ly_loadding.setVisibility(View.VISIBLE);
                STATUS_CURRENT = LgLinearLayout.STATUS_FAIL;
                tv_load.setVisibility(View.VISIBLE);
                iv_load.setVisibility(View.VISIBLE);
                pb_load.setVisibility(View.GONE);
                tv_load.setText(BaseApplication.getResource().getString(R.string.load_fail));
                break;

            case LgLinearLayout.STATUS_END:
                STATUS_CURRENT = LgLinearLayout.STATUS_END;
                ly_loadding.setVisibility(View.GONE);
                break;
        }
    }


}
