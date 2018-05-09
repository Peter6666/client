package com.logic.client.net;

import com.logic.client.bean.GankData;
import com.logic.client.bean.IdataNews;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/23
 * @desc
 */

public  interface OkService {

    @GET("login")
    Observable<ResponseBody> login(@Query("username") String username, @Query("password") String password);

    @GET(OkConstants.ANDROID+"{size}/{page}")
    Observable<GankData> getAndroidList(
            @Path("size") int size,
            @Path("page") int page);

    @GET(OkConstants.IOS+"{size}/{page}")
    Observable<GankData> getIOSList(
            @Path("size") int size,
            @Path("page") int page);

    @GET(OkConstants.WELFARE+"{size}/{page}")
    Observable<GankData> getWelfareList(
            @Path("size") int size,
            @Path("page") int page);

    @GET("qihoo?apikey="+OkConstants.IDATA_APPKEY)
    Observable<IdataNews> getIndataNews(
            @Query("kw") String kw,
            @Query("pageToken") int page,
            @Query("site") String site
            );

}
