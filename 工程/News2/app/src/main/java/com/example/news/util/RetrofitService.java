package com.example.news.util;


import com.example.news.bean.TodayBean;
import com.example.news.bean.VideoUrlBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface RetrofitService {

    /*
     * http://is.snssdk.com/api/news/feed/v51/?category=video
     * */
    @GET("news/feed/v51/")
    Observable<TodayBean> getToday(@Query("category") String category);

    /*
     * http://ib.365yg.com/video/urls/v/1/toutiao/mp4/v02004f00000bbpbk3l2v325q7lmkds0?r=6781281688452415&s=2734808831
     * */
    @GET
    Observable<VideoUrlBean> getVideoUrl(@Url String url);


}
