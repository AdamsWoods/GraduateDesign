package com.example.news.video.model;

import com.example.news.bean.TodayContentBean;
import com.example.news.bean.VideoUrlBean;

import java.util.List;

public interface IVideoLoadListener {
    void videoUrlSuccess(List<VideoUrlBean> videoUrlBeans, List<TodayContentBean> contentBeans);
    void fail(Throwable throwable);
}
