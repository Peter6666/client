package com.logic.client.rx.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.logic.client.rx.RxBus;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/23
 * @desc
 */

public class BaseApplication extends Application {

    private static BaseApplication mContext;
    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        resources = mContext.getResources();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static Resources getResource(){
        return resources;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
