package com.example.news.news.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.news.BasePresenter;
import com.example.news.BaseView;
import com.example.news.bean.NewsContent.NewsItem;

import java.util.ArrayList;

public interface NewsFrgContract {

    interface View extends BaseView<Presenter>{
        void showMessage(String message);
        void showNewsDetail(String url, String title);
        void onSuccessed(String tag, ArrayList<NewsItem> list);
        void onFailed(String tag, Exception e);
        void onFresh(String tag, ArrayList<NewsItem> list);
    }

    interface Presenter extends BasePresenter {
        void start(String title);
        void loadNews(boolean forceUpdate, String title);
        void openNewsDetails(Context context, NewsItem item);
        void refreshNews();
        void reLoadNews(String title);
        void addHistory(Context context, NewsItem item);
    }

    interface CallBack {
        void onSuccessed(String tag, Object object);
        void onFailed(String tag, Exception e);
    }
}
