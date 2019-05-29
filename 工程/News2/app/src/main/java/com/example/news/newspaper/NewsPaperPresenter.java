package com.example.news.newspaper;

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
import com.example.news.bean.NewsPaperBean;
import com.example.news.config.CONFIG;
import com.example.news.util.GsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsPaperPresenter implements NewsPaperContract.Presenter {

    private NewsPaperContract.View mView;
    private Context mcontext;
    private Handler handler = new Handler();

    public NewsPaperPresenter(Context context,NewsPaperFragment paperFragment){
        mView = paperFragment;
        mcontext = context;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (CONFIG.isFristLoadNewsPaper){
            loadNewsPaper();
            CONFIG.isFristLoadNewsPaper = false;
        }
    }

    @Override
    public void loadNewsPaper() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                final String url = CONFIG.Newspaper_URL;
                final String tag = "newspaper";
                final RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                requestQueue.cancelAll(tag);
                final StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (!TextUtils.isEmpty(response)){
                                    Log.e("response", response);
                                    NewsPaperBean result = GsonUtils.getInstance().
                                            fromJson(response, NewsPaperBean.class);
                                    addToDataBase(result.getResult());
//                                    mView.onSuccess(DbHelper.queryCity(mcontext));
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley:" + tag, error.getMessage(),error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("type", "newspaper");
                        return params;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void addToDataBase(final List<NewsPaperBean.ResultBean> list) {
//        DbHelper.insertNewsPaper(mcontext, list.get(0));
        DbHelper.deleteTable(mcontext);
//        Log.d("delet table", )
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++){
                    DbHelper.insertNewsPaper(mcontext, list.get(i));
                }
                List<String> list1 = DbHelper.queryCity(mcontext);
                Log.d("query", list1.size() + "");
                mView.onSuccess(list1);
            }
        });
    }
}
