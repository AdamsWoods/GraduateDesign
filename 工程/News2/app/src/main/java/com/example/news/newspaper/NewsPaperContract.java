package com.example.news.newspaper;

import android.content.Context;

import com.example.news.BasePresenter;
import com.example.news.BaseView;
import com.example.news.bean.NewsPaperBean;

import java.util.List;

public interface NewsPaperContract {

    interface View extends BaseView {
        void onSuccess(List<String> list);
        void onBack();
    }

    interface Presenter extends BasePresenter{
        void loadNewsPaper();
        void addToDataBase(List<NewsPaperBean.ResultBean> list);
    }
}
