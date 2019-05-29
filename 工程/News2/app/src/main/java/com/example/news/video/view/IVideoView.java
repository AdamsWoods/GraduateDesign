package com.example.news.video.view;

import com.example.news.bean.TodayContentBean;

import java.util.List;

public interface IVideoView {
    void showVideo(List<TodayContentBean> todayContentBeans, List<String> videoList);
    void hideDialog();
    void showDialog();
    void showErrorMsg(Throwable throwable);
}