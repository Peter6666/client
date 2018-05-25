package com.logic.client.net;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/23
 * @desc
 */

public class OkConstants {

    public static final int TYPE_COUNT = 3;
    public static final int TYPE_GANK_HOST = 0;
    public static final int TYPE_ALI_HOST = 1;
    public static final int TYPE_IDATA_HOST = 2;
    public static final int TYPE_NETEAST_HOST = 3;
    public static final int TYPE_LIVE_HOST = 4;



    public static final String GANK_HOST = "http://gank.io/api/";//福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
    public static final String ANDROID = "data/Android/";//data/Android/10/1请求个数： 数字，大于0 第几页：数字，大于0
    public static final String WELFARE = "data/福利/";//data/Android/10/1请求个数： 数字，大于0 第几页：数字，大于0
    public static final String IOS = "data/iOS/";//data/Android/10/1请求个数： 数字，大于0 第几页：数字，大于0
    public static final String ALL = "data/all/";//data/Android/10/1请求个数： 数字，大于0 第几页：数字，大于0
    public static final String RANDOM = "random/data/";//Android/20


    public static final String ALI_APPKEY = "25c547d942314331b6495bca3a88e5e8";
    public static final String ALI_AUTHORIZATION = "APPCODE 25c547d942314331b6495bca3a88e5e8";
    public static final String ALI_HOST = "http://toutiao-ali.juheapi.com/toutiao/index/";//top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)

    //idataapi
//    http://api01.bitspaceman.com:8000/news/qihoo?apikey={您自己的apikey}
    public static final String IDATA_HOST = "http://api01.bitspaceman.com:8000/news/";
    public static final String IDATA_APPKEY = "jcAQAjAOiG2XsYK7nxJvvfxm944yUOzAV0UU9e0gEKifjJUAIYBjdTzjPvDsho1Z";


    public static final String NETEAST_HOST = "http://c.m.163.com/";
    public static final String END_URL = "-20.html";
    public static final String ENDDETAIL_URL = "/full.html";

    // 新闻详情
    public static final String NEWS_DETAIL = NETEAST_HOST + "nc/article/";
    // 头条TYPE
    public static final String HEADLINE_TYPE = "headline";
    // 房产TYPE
    public static final String HOUSE_TYPE = "house";
    // 其他TYPE
    public static final String OTHER_TYPE = "list";


    public static final String LIVE_HOST = "http://www.quanmin.tv/";
}
