package com.example.news.history;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.bean.HistoryBean;
import com.example.news.bean.NewsContent;
import com.example.news.config.CONFIG;
import com.example.news.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryPresenter implements HistoryContract.HistoryPresenter {

    private HistoryContract.View mView;
    private Handler mHandler = new Handler();
    private Context context;

    public HistoryPresenter(HistoryContract.View view, Context context){
        mView = view;
        this.context = context;

        mView.setPresenter(this);
    }


    @Override
    public void start() {
        loadHistory(context);
    }

    @Override
    public void loadHistory(final Context context) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String url = CONFIG.History_URL_query;
                final String tag = "history";
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
                                    HistoryBean historyData = GsonUtils.getInstance().fromJson(response, HistoryBean.class);
                                    List<HistoryBean.RecordBean> result = historyData.getRecord();
                                    ArrayList<NewsContent.NewsItem> list = new ArrayList<>(0);
                                    for (int i = 0; i < result.size(); i++) {
                                        Log.e("history转化：", result.size()+" "+ result.get(i).getTitle());
                                        NewsContent.NewsItem item = new NewsContent.NewsItem(result.get(i).getImgurl(),
                                                result.get(i).getTitle(), result.get(i).getDetails());
                                        list.add(item);
                                    }
                                    mView.onSuccess(list);
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
                        params.put("AccountNumber", CONFIG.USER_NAME);
                        params.put("type","history");
                        return params;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void loadHistory(final Context context, boolean reload) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String url = CONFIG.History_URL_query;
                final String tag = "history";
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
                                    HistoryBean historyData = GsonUtils.getInstance().fromJson(response, HistoryBean.class);
                                    List<HistoryBean.RecordBean> result = historyData.getRecord();
                                    ArrayList<NewsContent.NewsItem> list = new ArrayList<>(0);
                                    for (int i = 0; i < result.size(); i++) {
                                        Log.e("history转化：", result.size()+" "+ result.get(i).getTitle());
                                        NewsContent.NewsItem item = new NewsContent.NewsItem(result.get(i).getImgurl(),
                                                result.get(i).getTitle(), result.get(i).getDetails());
                                        list.add(item);
                                    }
                                    mView.onSuccess(list);
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
                        params.put("AccountNumber", CONFIG.USER_NAME);
                        params.put("Type","history");
                        return params;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void openNewsDetails(Context context, NewsContent.NewsItem item) {
        mView.openNews(item);
    }
}
