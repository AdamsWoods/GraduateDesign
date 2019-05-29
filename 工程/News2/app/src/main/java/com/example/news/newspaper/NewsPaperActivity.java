package com.example.news.newspaper;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.news.R;
import com.example.news.util.ActivityUtils;

public class NewsPaperActivity extends AppCompatActivity {

    NewsPaperFragment newsPaperFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspaper_act);

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        newsPaperFragment = (NewsPaperFragment) getSupportFragmentManager().findFragmentById(R.id.newspaperFrame);

        if (newsPaperFragment == null){
            newsPaperFragment = NewsPaperFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), newsPaperFragment, R.id.newspaperFrame);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        newsPaperFragment.onBack();
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        newsPaperFragment.onBack();
        return false;
    }
}
