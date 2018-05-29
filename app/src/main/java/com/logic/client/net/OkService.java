package com.logic.client.net;

import com.logic.client.bean.GankData;
import com.logic.client.bean.IdataNews;
import com.logic.client.bean.LiveChannel;
import com.logic.client.bean.LiveChannelTabs;
import com.logic.client.bean.LiveRoom;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/23
 * @desc
 */

public interface OkService {

    @GET("login")
    Observable<ResponseBody> login(@Query("username") String username, @Query("password") String password);

    @GET(OkConstants.ANDROID + "{size}/{page}")
    Observable<GankData> getAndroidList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET(OkConstants.IOS + "{size}/{page}")
    Observable<GankData> getIOSList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET(OkConstants.WELFARE + "{size}/{page}")
    Observable<GankData> getWelfareList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET("qihoo?apikey=" + OkConstants.IDATA_APPKEY)
    Observable<IdataNews> getIndataNews(
            @Header("Cache-Control") String cacheControl,
            @Query("kw") String kw,
            @Query("pageToken") int page,
            @Query("site") String site
    );

//    /**
//     * 获取App启动页信息
//     * @return
//     */
//    @GET("json/page/app-data/info.json?v=3.0.1&os=1&ver=4")
//    Observable<AppStart> getAppStartInfo();

    /**
     * 获取分类列表
     *
     * @return categories/list.json
     */
    @GET("json/app/index/category/info-android.json?v=3.0.1&os=1&ver=4")
    Observable<List<LiveChannelTabs>> getLiveChannelTabs(@Header("Cache-Control") String cacheControl);
//
//    /**
//     * 获取推荐列表
//     * @return
//     */
//    @GET("json/app/index/recommend/list-android.json?v=3.0.1&os=1&ver=4")
//    Observable<Recommend> getRecommend();
//
//    /**
//     * 获取直播列表
//     * @return
//     */
//    @GET("json/play/list.json?v=3.0.1&os=1&ver=4")
//    Observable<LiveListResult> getLiveListResult();
//

    /**
     * 通过频道获取获取直播列表
     * @return
     */
    @GET("json/categories/{slug}/list.json?v=3.0.1&os=1&ver=4")
    Observable<LiveChannel> getLiveListByChannel(@Header("Cache-Control") String cacheControl, @Path("slug") String slug);

    /**
     * 进入房间
     * @param uid
     * @return
     */
    @GET("json/rooms/{uid}/info.json?v=3.0.1&os=1&ver=4")
    Observable<LiveRoom> getLiveRoom(@Path("uid")String uid);

//    /**
//     * 搜索
//     * @param searchRequestBody
//     * @return
//     */
//    @POST("site/search")
//    Observable<SearchResult> search(@Body SearchRequestBody searchRequestBody);

}
