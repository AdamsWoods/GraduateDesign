package com.android602_4.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android602_4.MainActivity;
import com.android602_4.R;

/**
 * Created by 1 on 2018/6/30.
 */

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 3000L;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },DELAY_TIME);
    }
}
