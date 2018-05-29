package com.logic.client.rx.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.logic.client.rx.RxBus;

import org.polaric.colorful.Colorful;

import io.vov.vitamio.Vitamio;

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
        boolean initialized = Vitamio.isInitialized(mContext);
        Colorful.init(mContext);
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
