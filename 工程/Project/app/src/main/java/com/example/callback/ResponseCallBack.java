package com.example.callback;

import com.alibaba.fastjson.JSON;
import com.example.bean.JsonBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class ResponseCallBack extends Callback {

    @Override
    public Object parseNetworkResponse(Response response, int i) throws Exception {
        String string = response.body().string();
        return JSON.parseObject(string, JsonBean.class);
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onResponse(Object o, int i) {

    }
}
