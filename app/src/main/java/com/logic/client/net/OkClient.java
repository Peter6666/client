package com.logic.client.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/20
 * @desc
 */

public class OkClient {


    private static final int READ_TIME_OUT = 8000;//读超时 ms
    private static final int CONNECT_TIME_OUT = 8000;//连接时 ms
    private Retrofit mRetrofit;
    private OkService mOkService;
    private OkHttpClient okHttpClient;
    private String host;


    public OkClient(int type) {
        //缓存
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache1");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };


        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .cache(cache)
                .build();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//retrofit2 adapter-rxjava目前还不支持Rxjava 2.x 是用retrofit2-rxjava2-adapter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//retrofit2-rxjava2-adapter
                .baseUrl(getHost(type))
                .build();
        mOkService = mRetrofit.create(OkService.class);

    }

    private static SparseArray<OkClient> sRetrofitManager = new SparseArray<>(OkConstants.TYPE_COUNT);
//    public static OkHttpClient getOkHttpClient() {
//        OkClient retrofitManager = sRetrofitManager.get(OkService.NETEASE_NEWS_VIDEO);
//        if (retrofitManager == null) {
//            retrofitManager = new OkClient(OkService.NETEASE_NEWS_VIDEO);
//            sRetrofitManager.put(OkService.NETEASE_NEWS_VIDEO, retrofitManager);
//        }
//        return retrofitManager.okHttpClient;
//    }


    public static OkService getDefault(int type) {
        OkClient retrofitManager = sRetrofitManager.get(type);
        if (retrofitManager == null) {
            retrofitManager = new OkClient(type);
            sRetrofitManager.put(type, retrofitManager);
        }
        return retrofitManager.mOkService;
    }



    /**
     * 设缓存有效期
     */
    private static final long CACHE_VALIDITY = 60 * 60 * 24 * 1;

    public String getHost(int type) {

        if (type == OkConstants.TYPE_GANK_HOST)
            return OkConstants.GANK_HOST;
        else if (type == OkConstants.TYPE_ALI_HOST)
            return OkConstants.ALI_HOST;
        else if (type == OkConstants.TYPE_IDATA_HOST)
            return OkConstants.IDATA_HOST;
        else if (type == OkConstants.TYPE_NETEAST_HOST)
            return OkConstants.NETEAST_HOST;
        else if (type == OkConstants.TYPE_LIVE_HOST)
            return OkConstants.LIVE_HOST;

            return OkConstants.GANK_HOST;
    }


    /**
     * 设缓存有效期
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 20;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age="+60*5;

    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(BaseApplication.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl)? CacheControl.FORCE_NETWORK:CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_VALIDITY)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}
