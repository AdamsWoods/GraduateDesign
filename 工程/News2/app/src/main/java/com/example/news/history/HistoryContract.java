package com.example.news.history;

import android.content.Context;

import com.example.news.BasePresenter;
import com.example.news.BaseView;
import com.example.news.bean.HistoryVideoBean;
import com.example.news.bean.NewsContent;

import java.util.ArrayList;

public interface HistoryContract {

    interface View extends BaseView {
        void showMessage(String message);
        void onSuccess(ArrayList<NewsContent.NewsItem> items);
        void openNews(NewsContent.NewsItem item);
    }

    interface HistoryPresenter extends BasePresenter{
        void loadHistory(Context context);
        void loadHistory(Context context, boolean reload);
        void openNewsDetails(Context context, NewsContent.NewsItem item);
    }

    interface VideoView extends BaseView{
        void showVideo();
        void hideDialog();
        void showDialog();
        void onSuccess(ArrayList<HistoryVideoBean.ResultBean> list);
    }

    interface  HistoryVideoPresenter extends BasePresenter{
        void loadHistory();
    }
}
