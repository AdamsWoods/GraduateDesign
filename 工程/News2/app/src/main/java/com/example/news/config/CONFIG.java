package com.example.news.config;

public class CONFIG {
    public static boolean LOGINSTATUS = false;
    public static String USER_NAME;
    public static String Head_URL;
    public static boolean isFristLoadNewsPaper = true;

    public static final String FEEDBACK_URL = "https://support.qq.com/product/60457";
    public static final String Login_URL = "http://24tz764373.qicp.vip/News2Service/servlet/LoginServlet";
    public static final String Register_URL = "http://24tz764373.qicp.vip/News2Service/servlet/RegisterServlet";
    public static final String History_URL = "http://24tz764373.qicp.vip/News2Service/servlet/HistoryAddServlet";
    public static final String History_URL_query = "http://24tz764373.qicp.vip/News2Service/servlet/HistoryQueryServlet";
    public static final String TODAY_HOST = "http://is.snssdk.com/api/";
    public static final String ProjectSource = "https://github.com/AdamsWoods/GraduateDesign/tree/master/%E5%B7%A5%E7%A8%8B";
    public static final String Newspaper_URL = "http://24tz764373.qicp.vip/News2Service/servlet/NewsPaperServlet";
    public static final String Weather_URL = "https://www.tianqiapi.com/api/?version=v6";

    public class API {
        public static final String URL = "http://v.juhe.cn/toutiao/index?type=";
        public static final String WEATHER_URL = "http://apis.juhe.cn/simpleWeather/query?city=%E8%8B%8F%E5%B7%9E&key=";
        public static final String KEY = "530d98e8f59f3ab48e9bf335e39b760f";
    }

}
