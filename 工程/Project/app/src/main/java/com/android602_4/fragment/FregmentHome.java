package com.android602_4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android602_4.HomeDetailsActivity;
import com.android602_4.R;
import com.android602_4.TalkDetailsActivity;
import com.android602_4.adapter.HomeListAdapter;
import com.android602_4.bean.HomeListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FregmentHome extends BaseFragment implements ViewPager.OnPageChangeListener{

    private List<HomeListItem> listItems = new ArrayList<>();
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

    private HomeListAdapter listAdapter;
    private ListView listView;

    private String content = "      回忆一下你去过的旅游区厕所，里面耗品是否定时补充？卫生能不能实时保洁？破烂有没有及时维修？\n" +
            "       如果答案是否定的，那么这些现象正是视察组所指出的问题：管理水平不高、服务质量不优、人文关怀不够。\n" +
            "       管理者往往担心：“有些人一次取走很多免费厕纸，抬高了厕所运行成本。”\n" +
            "       怎么办？\n" +
            "       委员们建议，推行建立旅游厕所管理责任制和厕所管理服务市场化。\n" +
            "       一方面，进一步完善旅游区厕所管理、服务和使用规范标准，借鉴“河长制”“湖长制”经验，探索建立旅游厕所“所长制”，将旅游区厕所建设、管理达标与否作为旅游景区、风景名胜区级别评定的重要依据。\n" +
            "       另一方面，坚持政府补贴和市场运作相结合，将旅游厕所管理维护委托第三方，培植厕所经营管理公司新业态。\"";

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_home, container, false);
        intitView(view);
        initData();
        intitAdapter();
        return view;
    }

    private void intitView(View view){
        headViewPager = (ViewPager) view.findViewById(R.id.fragment_head);
        headPointContainer = (LinearLayout) view.findViewById(R.id.home_point_container);
        listView = (ListView) view.findViewById(R.id.home_list);
    }

    private void intitAdapter() {
        //声明listviewadapter
        listAdapter = new HomeListAdapter(getActivity(), R.layout.list_item_home, listItems);
        //给ListView绑定数据
        listView.setAdapter(listAdapter);
        //更新适配器数据
        listAdapter.notifyDataSetChanged();
        //设置点击监听
        listView.setOnItemClickListener(onItemClickListener);

        //初始化轮播适配器
        headViewPager.setAdapter(new TopPagerAdapter());
        headViewPager.setFocusable(true);
        headViewPager.setCurrentItem(currentItem);
        //开始轮播
        start();
    }

    //开始轮播
    private void start() {
        headViewPager.addOnPageChangeListener(this);
        isRunning = true;
        mHandler.postDelayed(task,3000);
    }

    private void initData() {
        //listview获取数据
        getData();

        //head轮播获取数据
        ImageView imageView;
        ImageView pointView;
        LinearLayout.LayoutParams layoutParams;
        dotsList.clear();
        headPointContainer.removeAllViews();
        for (int i = 0; i< imageId.length;i++){
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageId[i]);
            imageViewList.add(imageView);

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

    //设置数据源，需要从网络获取
    private void getData() {
        if (flag == 0){
            for (int i = 0; i < 20; i++) {
                //手写假的数据
                HomeListItem item = new HomeListItem(this.getString(R.string.list_home_item_tv),
                        R.mipmap.list_item_home);
                listItems.add(item);
            }
            flag = 1;
        }
    }

    //每个item点击监听
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), HomeDetailsActivity.class);
            String title = listItems.get(position).getText();
            int imgId = listItems.get(position).getImgId();
            intent.putExtra("title",title);
            intent.putExtra("image",imgId);
            intent.putExtra("content",content);
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
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
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
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
//            headViewPager.removeView(imageViewList.get(currentItem));
            headViewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
//            headViewPager.removeAllViews();
        }
    }

}


