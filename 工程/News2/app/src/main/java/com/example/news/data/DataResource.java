package com.example.news.data;

public interface DataResource {

    interface LoadNewsCallBack {
        void onNewsLoaded();
        void onNewsNotAvaliable();
    }

    interface GetNewsCallBack {
        void onNewsLoaded();
        void onNewsNotAvaliable();
    }

}
