package com.example.news.news;

import com.example.news.BasePresenter;
import com.example.news.BaseView;
import com.example.news.bean.WeatherBean;

public interface NewsContract {

    interface View extends BaseView<Presenter>{
        void loadWeatherSuccess(WeatherBean bean);
    }

    interface Presenter extends BasePresenter{
        void loadNews(boolean forceUpdate);
        void openNewsDetails();

    }

    interface Callback{
        void onSuccessed(String tag, Object object);
        void onFailed(String tag, Exception e);
    }

}
