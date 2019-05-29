package com.example.news.news.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.CallBack;
import com.example.news.bean.LoginData;
import com.example.news.bean.NewsContent.NewsItem;
import com.example.news.bean.NewsData;
import com.example.news.config.CONFIG;
import com.example.news.util.GsonUtils;
import com.example.news.util.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsFrgPresenter implements NewsFrgContract.Presenter {

    private NewsFrgContract.View mNewsView;
    private boolean mFristLoad = false;
    private String mTitle;
    private ArrayList<NewsData.ResultBean.DataBean> mlist = new ArrayList<>(0);
    private NewsItem lastNewsItem;
    private Handler mHandler = new Handler();

    public NewsFrgPresenter(NewsFrgContract.View view){
        this.mNewsView = view;
        mNewsView.setPresenter(this);
    }

    @Override
    public void start() {
//        loadNews(false);
    }

    @Override
    public void start(String title) {
        switch (title){
            case "头条":
                mTitle = "top";
                break;
            case "社会":
                mTitle = "shehui";
                break;
            case "国内":
                mTitle = "guonei";
                break;
            case "娱乐":
                mTitle = "yule";
                break;
            case "体育":
                mTitle = "tiyu";
                break;
            case "军事":
                mTitle = "junshi";
                break;
            case "科技":
                mTitle = "keji";
                break;
            case "财经":
                mTitle = "caijing";
                break;
            case "时尚":
                mTitle = "shishang";
                break;
        }
        loadNews(false,mTitle);
    }

    @Override
    public void loadNews(boolean forceUpdate, String title) {
        loadNews(forceUpdate || mFristLoad, true, title);
        mFristLoad = false;
    }

    private void loadNews(boolean forceUpdate, boolean showLoadingUi, final String title){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().get(CONFIG.API.URL, title, new CallBack() {
                    @Override
                    public void onSuccessed(String tag, Object object) {
                        NewsData newsData = (NewsData) object;
                        ArrayList<NewsItem> list = new ArrayList<>(0);
//                        Log.e(tag, newsData.getResult().getData().toString());
//                if (newsData.getResult().getStat().equals("10012")){
                        if (newsData == null){
                            onFailed(tag, new Exception());
                        }else {
                            for (NewsData.ResultBean.DataBean newsItem : newsData.getResult().getData()){
                                NewsItem newsItem1 =  new NewsItem( newsItem.getThumbnail_pic_s(),
                                        newsItem.getTitle(), newsItem.getUrl());
                                list.add(newsItem1);
                            }
                            mlist.addAll(newsData.getResult().getData());
                            mNewsView.onSuccessed(tag, list);
                        }
//                }
                    }

                    @Override
                    public void onFailed(String tag, Exception e) {
                        mNewsView.showMessage(e.getMessage());
                    }
                }, NewsData.class);
            }
        });
    }

    @Override
    public void openNewsDetails(Context context, NewsItem item) {
        mNewsView.showNewsDetail(item.details, item.content);
        if (CONFIG.USER_NAME != null && CONFIG.LOGINSTATUS ){
            Log.e("插入历史：",CONFIG.USER_NAME + CONFIG.LOGINSTATUS);
            addHistory(context, item);
        }
    }

    @Override
    public void refreshNews() {
        reLoadNews(mTitle);
//        loadNews(true, true, mTitle);
    }

    @Override
    public void reLoadNews(final String title) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                HttpUtils.getInstance().get(CONFIG.API.URL, title, new CallBack() {
                    @Override
                    public void onSuccessed(String tag, Object object) {
                        NewsData newsData = (NewsData) object;
//                        Log.e(tag, newsData.getResult().getData().toString());
                        ArrayList<NewsItem> list = new ArrayList<>(0);
                        if (newsData != null){
                            for (NewsData.ResultBean.DataBean newsItem : newsData.getResult().getData()){
                                for (NewsData.ResultBean.DataBean item : mlist)
                                    if(newsItem.getTitle().equals(item.getTitle())){
                                        mNewsView.showMessage("Already the latest!");
                                        return;
                                    }else {
                                        NewsItem newsItem1 =  new NewsItem( newsItem.getThumbnail_pic_s(), newsItem.getTitle(), newsItem.getUrl());
                                        list.add(newsItem1);
                                    }
                            }
//                    mList.addAll(list);
                            mNewsView.onFresh(tag, list);
                        }
                    }

                    @Override
                    public void onFailed(String tag, Exception e) {
                        mNewsView.showMessage(e.getMessage());
                    }
                }, NewsData.class);
            }
        });
    }

    @Override
    public void addHistory(final Context context, final NewsItem item) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                final String url = CONFIG.History_URL;
                final String tag = "History";
                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                //防止重复请求
                requestQueue.cancelAll(tag);
                final StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                LoginData result = GsonUtils.getInstance().fromJson(response, LoginData.class);
                                if (result.getResult().equals("insert success")){
                                    Log.e(tag, "历史插入成功！");
                                }else
                                    Log.e(tag, "历史插入失败！");
                            }
                        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley:"+tag, error.getMessage(), error);
                    }
                })  {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AccountNumber", CONFIG.USER_NAME);
                        params.put("type", "history");
                        params.put("Title", item.content);
                        params.put("Imgurl", item.imgUrl);
                        params.put("Details",item.details);
                        return params;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);
            }
        });
    }
}
