package com.example.news.login;

import android.content.Context;

import com.example.news.BasePresenter;
import com.example.news.BaseView;

public interface LoginContact {

    interface View extends BaseView{
        void attemptLogin();
        void showMessage(String message);
        void setHeadImg(String url);
    }

    interface Presenter extends BasePresenter{
        boolean isAccountNumberValid(String username);
        boolean isPasswordValid(String password);
        boolean isCodeValid(String code);
        int loginRequest(Context context, String username, String password, LoginActivity.LoginCallBack callBack);
        void time(String time);
        void smsRequest(String phone);
        void verifySmsCode(String phone);
        int setDialog(LoginActivity context, String phone, String password, LoginActivity.LoginCallBack callBack);
        int registerRequest(Context context, String username, String password, LoginActivity.LoginCallBack callBack);
    }
}
