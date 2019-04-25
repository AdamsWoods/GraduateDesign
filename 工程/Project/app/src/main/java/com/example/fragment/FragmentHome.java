package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.CONFIG;
import com.example.HomeDetailsActivity;
import com.example.R;
import com.example.adapter.HomeListAdapter;
import com.example.bean.HomeListItem;
import com.example.bean.JsonBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class FragmentHome extends BaseFragment implements ViewPager.OnPageChangeListener{

    private int flag = 0;
    private ViewPager headViewPager;
    private LinearLayout headPointContainer;
    //轮播图片id
    private int[] imageId = {
            R.mipmap.home1,R.mipmap.home2,R.mipmap.home3,R.mipmap.home4,R.mipmap.home5
    };
    //存放轮播图片
    private List<ImageView> imageViewList = new ArrayList<>();
    private List<ImageView> dotsList = new ArrayList<>();
    private int currentItem = 0;
    private boolean isRunning = false;
    private ScheduledExecutorService scheduledExecutorService;

    private ListView listView;
    private MHandler mHandler = new MHandler(this);

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_home, container, false);
        intitView(view);
        initData();
        intitAdapter();
        //开始轮播
        start();
        return view;
    }

    private void intitView(View view){
        headViewPager = view.findViewById(R.id.fragment_head);
        headPointContainer =  view.findViewById(R.id.home_point_container);
        listView = view.findViewById(R.id.home_list);
    }

    private void intitAdapter() {
        new MyTask(getActivity(), listView).execute();
        //设置点击监听
        listView.setOnItemClickListener(onItemClickListener);
        //初始化轮播适配器
        headViewPager.setAdapter(new TopPagerAdapter());
        headViewPager.setFocusable(true);
        headViewPager.setCurrentItem(currentItem);
    }

    //开始轮播
    private void start() {
        headViewPager.addOnPageChangeListener(this);
        isRunning = true;
        mHandler.postDelayed(task,3000);
    }

    private void initData() {
        //head轮播获取数据
        initHeadView();
    }

    private void initHeadView() {
        ImageView imageView, pointView;
        LinearLayout.LayoutParams layoutParams;
        dotsList.clear();
        headPointContainer.removeAllViews();
        for (int i = 0; i< imageId.length;i++){
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageId[i]);
            imageViewList.add(imageView);
            //下方指示点
            pointView = new ImageView(getActivity());
            if (i == 0)
                pointView.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                        R.drawable.home_dot_shape_foucus));
            else
                pointView.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                        R.drawable.home_dot_shape_unfoucus));
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = layoutParams.rightMargin = 4;
            headPointContainer.addView(pointView,layoutParams);
            dotsList.add(pointView);
        }
    }

    //每个item点击监听
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), HomeDetailsActivity.class);
            ListView listView = (ListView) parent;
            HomeListItem listItem = (HomeListItem) listView.getItemAtPosition(position);
//            String title = listItem.getText();
            String contentUrl = listItem.getHtml();
//            intent.putExtra("title",title);
            intent.putExtra("contentUrl",contentUrl);
//            Log.d("item信息：", title + contentUrl);
            startActivity(intent);
        }
    };

    //切换轮播线程任务
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                currentItem = (currentItem) % (imageViewList.size());
                headViewPager.setCurrentItem(currentItem);
                mHandler.postDelayed(task, 3000);
            } else {
                mHandler.postDelayed(task, 3000);
            }
        }
    };

    /**
     * 图片轮播任务
     *
     */
    private class ViewPageTask implements Runnable{
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageId.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
            headViewPager.removeAllViews();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsList.size(); i++) {
            if (i == position) {
                dotsList.get(i).setImageDrawable(ContextCompat.getDrawable(getActivity(),
                        R.drawable.home_dot_shape_foucus));
            } else {
                dotsList.get(i).setImageDrawable(ContextCompat.getDrawable(getActivity(),
                        R.drawable.home_dot_shape_unfoucus));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            //SCROLL_STATE_DRAGGING
            case 1:
                isRunning = false;
                break;
            //SCROLL_STATE_SETTLING
            case 2:
                isRunning = true;
                break;
            default:
                break;
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    static class MHandler extends Handler {

        WeakReference<FragmentHome> mActivityReference;

        MHandler(FragmentHome fregmentHome) {
            mActivityReference = new WeakReference<>(fregmentHome);
        }
        public void handleMessage(android.os.Message msg) {
            final FragmentHome fregmentHome = mActivityReference.get();
            fregmentHome.headViewPager.setCurrentItem(fregmentHome.currentItem);
        }
    }


    //轮播适配器
    private class TopPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position));
            return imageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private static class MyTask extends AsyncTask<String, Void, List<HomeListItem>> {

        private Context mContext;
        private ListView lv;

        public MyTask(Context mContext, ListView listView){
            this.mContext = mContext;
            this.lv = listView;
        }

        @Override
        protected void onPostExecute(List<HomeListItem> listItems) {
            if(listItems != null){
                HomeListAdapter homeListAdapter = new HomeListAdapter(mContext, R.layout.list_item_home, listItems);
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
                    JsonBean jsonBean = JSON.parseObject(s, JsonBean.class);
                    Log.d("异步任务：",jsonBean.getResult().getData().toString());
                    if(jsonBean.getResult()!=null)
                        for (JsonBean.ResultBean.DataBean dataBean : jsonBean.getResult().getData()) {
                            HomeListItem listItem = new HomeListItem(dataBean.getTitle(),
                                    dataBean.getThumbnail_pic_s(),
                                    dataBean.getUrl(),
                                    dataBean.getAuthor_name());
                            list.add(listItem);
                        }
                }
            });
            return list ;
        }
    }
}



