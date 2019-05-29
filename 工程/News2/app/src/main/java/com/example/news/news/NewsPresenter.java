package com.example.news.news;


import android.os.Handler;

import com.example.news.CallBack;
import com.example.news.bean.WeatherBean;
import com.example.news.config.CONFIG;
import com.example.news.util.HttpUtils;

public class NewsPresenter implements NewsContract.Presenter{

    private final NewsContract.View mNewsView;
    private boolean mFristLoad = true;
    private Handler mHandler = new Handler();

    public NewsPresenter(NewsContract.View newsView){
        mNewsView = newsView;

        mNewsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadNews(false);
        loadWeather();
    }

    @Override
    public void loadNews(boolean foreceUpdate) {
        loadNews(foreceUpdate || mFristLoad, true);
        mFristLoad = false;
    }

    private void loadNews(boolean foreceUpdate, boolean showLoadingUi){
    }

    private void loadWeather(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String url = CONFIG.Weather_URL;
                HttpUtils.getInstance().get(url, new CallBack() {
                            @Override
                            public void onSuccessed(String tag, Object object) {
                                mNewsView.loadWeatherSuccess((WeatherBean) object);
                            }

                            @Override
                            public void onFailed(String tag, Exception e) {
//                                mNewsView.showMessage(e.getMessage());
                            }
                        }, WeatherBean.class);
            }
        });
    }

    @Override
    public void openNewsDetails() {
    }
}
