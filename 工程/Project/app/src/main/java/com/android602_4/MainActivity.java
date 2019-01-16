package com.android602_4;

import android.animation.FloatEvaluator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.CycleInterpolator;
//import android.widget.LinearLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android602_4.adapter.ViewPagerAdapter;
import com.android602_4.fragment.FragmentNewspaper;
import com.android602_4.fragment.FragmentDiscovery;
import com.android602_4.fragment.FragmentTalk;
import com.android602_4.fragment.FregmentHome;
//import com.nineoldandroids.view.ViewHelper;
//import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

//import butterknife.Bind;
//import butterknife.ButterKnife;

//import static com.android602_4.R.id.layout_main;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TabLayout mTabLayout;
    private ViewPager mViewPager,headViewPager;
    private static MainActivity mInstance;
    private TitleBuilder mTitleBuilder;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ViewPagerAdapter viewPagerAdapter;

    private ImageView mTitleTV;
    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化标题栏
        initTitlebar();
        //初始化控件
        initView();
        //初始化tab
        initTab();
        initEvent();
    }

    //侧滑菜单按钮事件监听
    private void initEvent() {
        //tablayout监听，改变标题栏
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //选中时回调
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        mTitleBuilder.setLeftIcoVisiable(true);
                        mTitleBuilder.setTitleText(getResources().getString(R.string.app_name));
                        mTitleBuilder.setRightIco(R.mipmap.toolbar_open);
                        break;
                    case 1:
                        mTitleBuilder.setTitleText(getResources().getString(R.string.tab_title_talk));
                        mTitleBuilder.setRightIco(R.mipmap.toolbar_add);
                        break;
                    case 2:
                        mTitleBuilder.setTitleText(getResources().getString(R.string.tab_title_discovery));
                        mTitleBuilder.setRightIco(R.mipmap.toolbar_search);
                        break;
                    case 3:
                        mTitleBuilder.setTitleText(getResources().getString(R.string.tab_title_newspaper));
                        mTitleBuilder.setRightIco(R.mipmap.toolbar_share);
                        break;
                }
            }

            //未选择中回调
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //重新选择是huid
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //标题栏左边图片监听
        mTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerOpen(mNavigationView)){
                    mDrawerLayout.closeDrawer(mNavigationView);
                }else{
                    mDrawerLayout.openDrawer(mNavigationView);
                }
            }
        });
        //侧滑菜单每一个Item监听
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.slidemenu_login:
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                Toast.makeText(MainActivity.this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(mNavigationView);
                return true;
            }
        });
    }

    //初始化标题栏
    private void initTitlebar() {

        mTitleBuilder = new TitleBuilder(this).setLeftCircleIco(R.mipmap.toolbar_title)
                .setTitleText(getResources().getString(R.string.app_name)).setRightIco(R.mipmap.toolbar_open);

    }

    private void initTab(){
        //底部导航栏添加
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(new FregmentHome());
        arrayList.add(new FragmentTalk());
        arrayList.add(new FragmentDiscovery());
        arrayList.add(new FragmentNewspaper());
        //创建viewpager适配器
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPagerAdapter.setFragments(arrayList);
        //给viewpager设置适配器
        mViewPager.setAdapter(viewPagerAdapter);
        //tablayout 指示器
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        //使tablayout和viewpager相关联
        mTabLayout.setupWithViewPager(mViewPager);
        //添加指示器文本
        for (int i =0; i < mTabLayout.getTabCount(); i++){
            mTabLayout.getTabAt(i).setCustomView(viewPagerAdapter.getTabVeiw(i,mTabLayout));
        }
    }

    //初始化页面
    private void initView() {
        //底部导航栏
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //中间Fragement容器
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //侧滑菜单容器
        mNavigationView = (NavigationView) findViewById(R.id.nav);
        //整个activity的布局容器
        mDrawerLayout = (DrawerLayout) findViewById(R.id.atctivity_na);
        //标题栏左边图片按钮
        mTitleTV = (ImageView) findViewById(R.id.title_leftIco);
        //获取侧滑菜单栏头部图片
        View headerVeiw = mNavigationView.getHeaderView(0);
        //首页轮播容器
        headViewPager = (ViewPager) findViewById(R.id.fragment_head);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11){
            mViewPager.setCurrentItem(1);
        }else {
            if (requestCode == 2 && resultCode == 12){
                mViewPager.setCurrentItem(2);
            }else if (requestCode == 3 && resultCode == 13){
                mViewPager.setCurrentItem(3);
            }
        }
    }

    //获取本Activity实例
    public static Context getInstance(){
        if(mInstance == null){
            mInstance = new MainActivity();
        }
        return mInstance;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isRunning = false;
        MainActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            MainActivity.this.finish();
//            mViewPager.setCurrentItem(0);
//            exit();
        }
        return false;
    }

    private void exit(){
        if (!isExit && mViewPager.getCurrentItem()!=0){
            Toast.makeText(MainActivity.this,"tuichu111",Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(0);
            isExit = true;
        }else {
            if (isExit && mViewPager.getCurrentItem() == 0){
                Toast.makeText(MainActivity.this,"tuichu",Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        }
    }

}
