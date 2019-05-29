package com.example.news.login;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.bean.LoginData;
import com.example.news.config.CONFIG;
import com.example.news.util.GsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginPresenter implements LoginContact.Presenter {

    private LoginContact.View mView;
    public static int loginStatus ;
    public static int registerStatus ;
    private String phone;

    public LoginPresenter(LoginContact.View view){
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean isAccountNumberValid(String username) {
        String num="[1][3578]\\d{9}";
        return (username.length() == 11 && username.matches(num));
    }

    @Override
    public boolean isPasswordValid(String password) {
        /*
         * (?![0-9]+$)不全是数字
         * (?![a-zA-Z]+$)不全是字母
         * (?![0-9a-zA-Z]+$)为数字和字母的组合
         * 密码6到16位，
         */
        Pattern patterns = Pattern.compile( "^(?![0-9]+$)(?![a-zA-Z]+$)[?![0-9a-zA-Z]+$]{6,16}$");
        Matcher matcher = patterns.matcher(password);
//        String s = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![a-zA-Z]+$){6,16}$";
        return (!TextUtils.isEmpty(password) && matcher.matches());
    }

    @Override
    public boolean isCodeValid(String code) {
        return (code.length() == 4 && !TextUtils.isEmpty(code)) ;
    }

    @Override
    public int loginRequest(Context context, final String username, final String password, final LoginActivity.LoginCallBack callBack) {
        String url = CONFIG.Login_URL;
        final String tag = "Login";
        //取得请求队列
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        //防止重复请求
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag, response);
                        if (!TextUtils.isEmpty(response)){
                            LoginData loginData = GsonUtils.getInstance().fromJson(response, LoginData.class);
                            String result = loginData.getResult();
                            if (result.equals("success")){
                                CONFIG.LOGINSTATUS = true;
                                CONFIG.USER_NAME = username;
                                CONFIG.Head_URL = loginData.getImgurl();
                                Log.e("登陆成功：",CONFIG.USER_NAME + loginData.getImgurl());
                                loginStatus = 0;
                                callBack.onSuccess("success", loginData.getImgurl());
                            }
                            else if (result.equals("failed")){
                                loginStatus = 1;
                                callBack.onFail("failed", loginStatus);
//                                mView.mEmailView.setError("Account or Password is incrrect.");
//                                mEmailView.requestFocus();
                            } else if (result.equals("not found")){
                                loginStatus = 2;
                                callBack.onNotFound("not found", loginStatus);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley:"+tag, error.getMessage(), error);
                callBack.onFail("failed", error);
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", username);
                params.put("Password", password);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
        return loginStatus;
    }

    @Override
    public void time(final String time) {

    }

    //发送短信
    @Override
    public void smsRequest( String phone) {
        this.phone = phone;
        SMSSDK.registerEventHandler(new EventHandler(){
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (i1 == SMSSDK.RESULT_COMPLETE){
                    //send success
                    mView.showMessage("验证码发送成功");
                }else {
                    //send failed
                    mView.showMessage("验证码发送失败");
                }
            }
        });
        //获取验证码
        SMSSDK.getVerificationCode("86", phone);
    }

    @Override
    public void verifySmsCode(String code) {
        if (isCodeValid(code)){
            return ;
        }
        //注册一个回调事件 ，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //处理验证成功的结果
                    mView.showMessage("验证成功");
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //处理提交验证码成功，并通过验证
                        mView.showMessage("提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功，服务器验证码发送成功
                        mView.showMessage("获取验证码成功");
                    }
                } else {
                    //处理验证失败的结果
                }
            }
        });
        //触发操作，提交
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    @Override
    public int setDialog(final LoginActivity context, final String phone, final String password, final LoginActivity.LoginCallBack callBack) {
       final AlertDialog alertDialog = new AlertDialog.Builder(context)
               .setTitle("确定吗？")
               .setMessage("账号未注册，确定使用该账号并登陆？")
               .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                        registerRequest(context, phone, password, new LoginActivity.LoginCallBack(){

                            @Override
                            public void onSuccess(String tag, Object object) {
                                callBack.onSuccess("success", object);
                            }

                            @Override
                            public void onFail(String tag, Object e) {
                                callBack.onFail("success", 1);
                            }

                            @Override
                            public void onNotFound(String tag, Object object) {
                                callBack.onNotFound("success", 2);
                            }
                        });
                   }
               }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                   }
               })
               .create();
        alertDialog.show();
        return registerStatus;
    }

    @Override
    public int registerRequest(Context context, final String username, final String password, final LoginActivity.LoginCallBack callBack) {
        String url = CONFIG.Register_URL;
        final String tag = "Register";
        //取得请求队列
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        //防止重复请求
        requestQueue.cancelAll(tag);
        Log.e(tag, "开始request");
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag, response);
                        if (!TextUtils.isEmpty(response)){
                            LoginData loginData = GsonUtils.getInstance().fromJson(response, LoginData.class);
                            String result = loginData.getResult();
                            if (result.equals("success")){ //注册成功
                                CONFIG.LOGINSTATUS = true;
                                CONFIG.USER_NAME = username;
                                CONFIG.Head_URL = loginData.getImgurl();
                                callBack.onSuccess("success", loginData.getImgurl());
                            }
                            else if (result.equals("existed")){ //注册失败
                                callBack.onNotFound("existed",2);
                            } else {
                                callBack.onFail("failed", 1);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley:"+tag, error.getMessage(), error);
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", username);
                params.put("Password", password);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
        return loginStatus;
    }
}
