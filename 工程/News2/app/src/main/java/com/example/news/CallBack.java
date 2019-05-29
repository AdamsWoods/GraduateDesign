package com.example.news;

public interface CallBack {
    void onSuccessed(String tag, Object object);
    void onFailed(String tag, Exception e);
}
