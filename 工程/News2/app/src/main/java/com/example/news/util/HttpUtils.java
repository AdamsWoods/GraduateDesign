package com.example.news.util;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.news.CallBack;
import com.example.news.config.CONFIG;
import com.example.news.news.fragment.NewsFrgContract;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    private static Handler handler = new Handler();

    private static volatile HttpUtils instance;

    private HttpUtils(){}

    public static HttpUtils getInstance(){
        if (null == instance){
            synchronized (HttpUtils.class){
                if (instance == null)
                    instance = new HttpUtils();
            }
        }
        return instance;
    }

    public void get(String url, String type, final CallBack callBack, final Class cls){
        String path = url + type + "&key=" + CONFIG.API.KEY;
        OkHttpClient client = new OkHttpClient();
        Request build = new Request.Builder()
                .get()
                .url(path)
                .build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(TAG, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultStr = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Object object;
                        if (TextUtils.isEmpty(resultStr)){
                            object = null;
                            Log.i(TAG,"获取数据为空");
                        }else{
                            object = GsonUtils.getInstance().fromJson(resultStr, cls);
                        }
                        Log.e(TAG,object.toString());
                        callBack.onSuccessed(TAG, object);
                    }
                });
            }
        });
    }
  public void get(String url,  final CallBack callBack, final Class cls){
        OkHttpClient client = new OkHttpClient();
        Request build = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(TAG, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultStr = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Object object;
                        if (TextUtils.isEmpty(resultStr)){
                            object = null;
                            Log.i(TAG,"获取数据为空");
                        }else{
                            object = GsonUtils.getInstance().fromJson(resultStr, cls);
                        }
                        Log.e(TAG,object.toString());
                        callBack.onSuccessed(TAG, object);
                    }
                });
            }
        });
    }



}
