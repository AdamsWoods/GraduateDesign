package com.example.utils;

import android.util.JsonReader;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.CONFIG;
import com.example.bean.JsonBean;
import com.example.callback.ResponseCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class HttpUtils {

    public static void initData(List<?> list){
        String url = CONFIG.API.URL+"?type=top&key="+CONFIG.APPKEY.key;
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)//连接超时
                .readTimeout(10000L,TimeUnit.MILLISECONDS)//读取超时
                .build();//其他配置
        OkHttpUtils.initClient(okHttpClient);
        OkHttpUtils.get().url(url).build().execute(new ResponseCallBack());
    }

    private void processData(String json){
//        result = JSON.parseObject(json, JsonBean.class);
    }
}
