package com.example.news.feedback;

import android.webkit.WebView;
import android.widget.FrameLayout;

import com.example.news.BasePresenter;
import com.example.news.BaseView;

public interface FeedBackContract {

    interface View extends BaseView{
        void loadFeedBackView();
//        void goBack(WebView view);
    }

    interface Presenter extends BasePresenter{

    }
}
