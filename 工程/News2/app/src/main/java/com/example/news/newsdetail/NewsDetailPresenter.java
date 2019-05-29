package com.example.news.newsdetail;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private NewsDetailContract.View mView;
    private final String contentUrl;

    public NewsDetailPresenter(String url, NewsDetailContract.View view){
        mView = view;
        contentUrl = url;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadWebUrl();
    }

    @Override
    public void loadWebUrl() {
        mView.showDetail();
    }
}
