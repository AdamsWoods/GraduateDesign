package com.example.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.example.CONFIG;
import com.example.R;
import com.example.adapter.HomeListAdapter;
import com.example.bean.HomeListItem;
import com.example.bean.JsonBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class DownloadAsyncTask extends AsyncTask<String, Void, List<HomeListItem>> {
    private Context mContext;
    private ListView lv;

    public DownloadAsyncTask(Context mContext, ListView listView){
        this.mContext = mContext;
        this.lv = listView;
    }

    @Override
    protected void onPostExecute(List<HomeListItem> listItems) {
        super.onPostExecute(listItems);
        if(listItems != null){
            HomeListAdapter homeListAdapter = new HomeListAdapter(mContext, R.layout.list_item_home,listItems);
            lv.setAdapter(homeListAdapter);
        }
    }

    @Override
    protected List<HomeListItem> doInBackground(String... strings) {
        final List<HomeListItem> list = new ArrayList<>();
        String url = CONFIG.API.URL;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)//连接超时
                .readTimeout(10000L,TimeUnit.MILLISECONDS)//读取超时
                .build();//其他配置
        OkHttpUtils.initClient(okHttpClient);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                JsonBean jsonBean = new JsonBean();
                jsonBean = JSON.parseObject(s, JsonBean.class);
                Log.d("一步任务：",jsonBean.getResult().getData().toString());
                if(jsonBean.getResult()!=null)
                    for (JsonBean.ResultBean.DataBean dataBean : jsonBean.getResult().getData()) {
                        HomeListItem listItem = new HomeListItem(dataBean.getTitle(), dataBean.getThumbnail_pic_s(),dataBean.getUrl(),dataBean.getCategory());
                        list.add(listItem);
                    }
            }
        });
        return list ;
    }
}
