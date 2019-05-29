package com.example.news.video.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.bean.TodayContentBean;
import com.example.news.bean.VideoUrlBean;
import com.example.news.config.CONFIG;
import com.example.news.video.model.IVideoLoadListener;
import com.example.news.video.model.IVideoModel;
import com.example.news.video.model.VideoModel;
import com.example.news.video.view.IVideoView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.CRC32;

public class VideoPresenter implements IVideoPresenter, IVideoLoadListener {

    private IVideoModel iVideoModel;
    private IVideoView iVideoView;
    private Context context;
    private Handler handler = new Handler();

    public VideoPresenter(Context context, IVideoView iVideoView) {
        this.iVideoView = iVideoView;
        this.iVideoModel = new VideoModel();
        this.context = context;
    }

    @Override
    public void loadVideo() {
        iVideoView.showDialog();
        iVideoModel.loadVideo("video", this);
    }

    @Override
    public void videoUrlSuccess(List<VideoUrlBean> mainUrlBeans, List<TodayContentBean> contentBeans) {
        List<String> videoList = new ArrayList<>();
        List<TodayContentBean> contentList = new ArrayList<>();
        VideoUrlBean videoUrlBean;
        iVideoView.hideDialog();
//        if ()
        for (int i = 0; i < mainUrlBeans.size() - 1; i++) {
            Log.e("mainUrlBeans:",i+"");
            videoUrlBean = mainUrlBeans.get(i);
            if (videoUrlBean.getData() != null && videoUrlBean.getData().getVideo_list()!= null
                    && videoUrlBean.getData().getVideo_list().getVideo_1() != null) {
                String mainUrl = mainUrlBeans.get(i).getData().getVideo_list().getVideo_1().getMain_url();
                final String url1 = (new String(Base64.decode(mainUrl.getBytes(), Base64.DEFAULT)));
                videoList.add(url1);
                contentList.add(contentBeans.get(i));
            }
        }
        iVideoView.showVideo(contentList, videoList);
    }

    @Override
    public void fail(Throwable throwable) {
        iVideoView.hideDialog();
        iVideoView.showErrorMsg(throwable);
    }

    public static String getVideoContentApi(String videoid) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }

    public static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public static TodayContentBean getTodayContentBean(String content) {
        Gson gson = new Gson();
        TodayContentBean bean = gson.fromJson(content, TodayContentBean.class);
        return bean;
    }

    @Override
    public void addVideoHistory(final String title, final String video_url, final String bg_img) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                final String url = CONFIG.History_URL;
                final String tag = "history";
                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.cancelAll(tag);
                final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //获取返回数据
                        Log.d("history_video response", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley:"+tag, error.getMessage(), error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", "history_video");
                        map.put("AccountNumber", CONFIG.USER_NAME);
                        map.put("title", title);
                        map.put("url",video_url);
                        map.put("img", bg_img);
                        return map;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);
            }
        });
    }
}
