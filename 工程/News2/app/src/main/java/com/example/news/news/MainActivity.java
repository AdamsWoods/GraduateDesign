package com.example.news.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import com.bumptech.glide.Glide;
import com.example.news.bean.WeatherBean;
import com.example.news.config.CONFIG;
import com.example.news.feedback.FeedBackActivity;
import com.example.news.history.HistoryActivity;
import com.example.news.login.LoginActivity;
import com.example.news.newspaper.NewsPaperActivity;
import com.example.news.search.SearchPageActivity;
import com.example.news.setting.SettingActivity;
import com.example.news.setting.SettingsActivity;
import com.example.news.util.ActivityUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.bean.NewsContent;
import com.example.news.news.fragment.NewsItemFragment;
import com.example.news.video.VideoActivity;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, NewsItemFragment.OnListFragmentInteractionListener
        , NewsFragment.OnListFragmentInteractionListener {

    private NewsPresenter mNewsPresenter;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置floationActionButton
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //设置DrawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set newsFragment to activity
        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.newsFrg);
        if (newsFragment == null){
            //create fragment
            newsFragment = NewsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),newsFragment,R.id.newsFrg);
        }

        //create presenter
        mNewsPresenter = new NewsPresenter(newsFragment);

        //load previously saved state, if available

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CONFIG.Head_URL != null)
            Glide.with(this).load(CONFIG.Head_URL)
                    .into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView));
        if (CONFIG.USER_NAME != null){
            TextView username = navigationView.getHeaderView(0).findViewById(R.id.usernameTX);
            username.setText(CONFIG.USER_NAME.substring(0, 3)+"****"+CONFIG.USER_NAME.substring(7,11));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SearchPageActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            //Do nothing, we're already on that screen
        } else if (id == R.id.nav_video) {
            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_newspaper) {
            Intent intent = new Intent(this, NewsPaperActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(this, FeedBackActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(NewsContent.NewsItem item) {
//        Toast.makeText(this,"item"+item.id,Toast.LENGTH_LONG);
    }

    @Override
    public void onListFragmentInteraction(WeatherBean weather) {
        TextView tips = navigationView.getHeaderView(0).findViewById(R.id.tips);
        tips.setText(weather.getAir_tips());
        TextView tempture = navigationView.getHeaderView(0).findViewById(R.id.tempture);
        tempture.setText(weather.getTem()+ "℃");
        TextView weatherTV = navigationView.getHeaderView(0).findViewById(R.id.weather);
        weatherTV.setText(weather.getWea());
        TextView info = navigationView.getHeaderView(0).findViewById(R.id.info);
        info.setText(weather.getWin() + " "+weather.getWin_speed());
    }
}
