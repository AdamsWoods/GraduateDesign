package com.example.news.newsdetail;

import android.webkit.WebView;

import com.example.news.BasePresenter;
import com.example.news.BaseView;

public interface NewsDetailContract {

    interface  View extends BaseView{
        void showDetail();
//        void showShare();
    }

    interface Presenter extends BasePresenter{
        void loadWebUrl();
    }

}
