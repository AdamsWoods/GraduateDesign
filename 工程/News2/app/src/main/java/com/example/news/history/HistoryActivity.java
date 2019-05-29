package com.example.news.history;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.news.R;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private View mDecorView;
    private SegmentTabLayout mTablayout;
    private String[] title = {"新闻", "视频"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        //set status block color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mTablayout = findViewById(R.id.historyTab);
        final ViewPager viewPager = findViewById(R.id.historyVP);

        ArrayList<Fragment> listFrg = new ArrayList<>(0);
        for (String str : title){
            if (str.equals("新闻")){
                HistoryFragment historyFragment = HistoryFragment.newInstence(str);
                listFrg.add(historyFragment);
            }else if (str.equals("视频")){
                HistoryVideoFragment videoFragment = HistoryVideoFragment.newInstence();
                listFrg.add(videoFragment);
            }

        }

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setList(listFrg, title);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mTablayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(0);

        mTablayout.setTabData(title);
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

//        HistoryFragment historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentById(R.id.history_frame);
//        if (historyFragment == null){
//            historyFragment = HistoryFragment.newInstence();

//            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), historyFragment, R.id.history_frame);
//    }
//        new HistoryPresenter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
