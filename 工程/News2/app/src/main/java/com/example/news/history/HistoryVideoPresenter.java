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
import com.example.news.bean.HistoryVideoBean;
import com.example.news.bean.NewsContent;
import com.example.news.config.CONFIG;
import com.example.news.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryVideoPresenter implements HistoryContract.HistoryVideoPresenter {

    private HistoryContract.VideoView mView;
    private Context mcontext;
    private Handler mHandler = new Handler();

    public HistoryVideoPresenter (Context context, HistoryContract.VideoView videoView){
        this.mView = videoView;
        this.mcontext = context;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHistory();
    }

    @Override
    public void loadHistory() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String url = CONFIG.History_URL_query;
                final String tag = "history";
                //取得请求队列
                final RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                //防止重复请求
                requestQueue.cancelAll(tag);
                final StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e(tag, response);
                                if (!TextUtils.isEmpty(response)){
                                    HistoryVideoBean historyData = GsonUtils.getInstance().fromJson(response, HistoryVideoBean.class);
                                    List<HistoryVideoBean.ResultBean> result = historyData.getResult();
                                    ArrayList<HistoryVideoBean.ResultBean> list = new ArrayList<>(0);
                                    for (int i = 0; i < result.size(); i++) {
                                        Log.e("history转化：", result.size()+" "+ result.get(i).getVideo_title());
                                        HistoryVideoBean.ResultBean item = new HistoryVideoBean.ResultBean();
                                        item.setVideo_title(result.get(i).getVideo_title());
                                        item.setVideo_url(result.get(i).getVideo_url());
                                        item.setVideo_bg_img(result.get(i).getVideo_bg_img());
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
                        params.put("type","history_video");
                        return params;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);
            }
        });
    }
}
