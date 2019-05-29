package com.example.news.search;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.search.adapters.ContentAdapter1;
import com.example.news.search.adapters.ContentAdapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SamLeung
 * @Emial 729717222@qq.com
 * @date 2018/4/20 0020 17:24
 */
public class SearchPageActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRecommend1;
    private RecyclerView recyclerViewRecommend2;
    private RecyclerView recyclerViewHistory;
    private ImageView ivHistoryArrow;
    private TextView tvHistory;

    private ImageView ivHistoryDelete;
    private TextView tvHistoryDeleteFinish;
    private EditText searchTv;

    private LinearLayout llShowRecommendBar;
    private LinearLayout llRecommend1Bar;
    private LinearLayout llRecommend2Bar;

    private List<String> recommend1ContentList = new ArrayList<>();
    private List<String> recommend2ContentList = new ArrayList<>();
    private List<String> historyContentList = new ArrayList<>();

    private ContentAdapter1 recommend1Adapter;
    private ContentAdapter2 recommend2Adapter;
    private ContentAdapter2 historyAdapter;

    private boolean isShowRecommend = true; //是否显示推荐词栏
    private boolean isHidePartialHistory = true; //是否隐藏部分历史,默认为true
    private boolean isInHistoryDelete = false; //是否处于删除历史模式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpage_act);

        initData();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViews(){
        //沉浸式处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        searchTv = findViewById(R.id.search);
        searchTv.setOnKeyListener(keyListener);

        recyclerViewRecommend1 = findViewById(R.id.rv_recommend_bar_1);
        recyclerViewRecommend2 = findViewById(R.id.rv_recommend_bar_2);
        recyclerViewHistory = findViewById(R.id.rv_recommend_bar_history);

        tvHistory = findViewById(R.id.tv_history); //
        ivHistoryArrow = findViewById(R.id.iv_arrow);//历史记录展开
        ivHistoryDelete = findViewById(R.id.iv_delete);//历史记录删除按钮
        tvHistoryDeleteFinish = findViewById(R.id.tv_finish);
        llShowRecommendBar = findViewById(R.id.linear_show_recommend);//收起推荐
        llRecommend1Bar = findViewById(R.id.linear_recommendbar1);
        llRecommend2Bar = findViewById(R.id.linear_recommendbar2);

        DividerItemDecoration itemDecorationHor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorationHor.setDrawable(new ColorDrawable(Color.parseColor("#e0e0e0")));

        DividerItemDecoration itemDecorationVer = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorationVer.setDrawable(new ColorDrawable(Color.parseColor("#e0e0e0")));

        //搜索栏1
        recommend1Adapter = new ContentAdapter1(getApplicationContext(), recommend1ContentList);
        recommend1Adapter.setItemClickListener(recommend1BarItemClickListener);

        GridLayoutManager gmRecommend1 = new GridLayoutManager(getApplicationContext(), 3);
        recyclerViewRecommend1.setLayoutManager(gmRecommend1);
        recyclerViewRecommend1.addItemDecoration(itemDecorationHor);//设之分割线横竖
        recyclerViewRecommend1.addItemDecoration(itemDecorationVer);
        recyclerViewRecommend1.setAdapter(recommend1Adapter);

        //搜索栏2
        recommend2Adapter = new ContentAdapter2(getApplicationContext(), recommend2ContentList);
        recommend2Adapter.setItemClickListener(recommend2BarItemClickListener);

        GridLayoutManager gmRecommend2 = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewRecommend2.setLayoutManager(gmRecommend2);
        recyclerViewRecommend2.addItemDecoration(itemDecorationHor);//设之分割线横竖
        recyclerViewRecommend2.addItemDecoration(itemDecorationVer);
        recyclerViewRecommend2.setAdapter(recommend2Adapter);

        //历史记录
        historyAdapter = new ContentAdapter2(getApplicationContext(), historyContentList);
        //历史记录先不显示全部
        int displayCount = historyContentList.size() > 4 ? 4 : historyContentList.size();
        historyAdapter.setDisplayCount(displayCount);
        historyAdapter.setItemClickListener(historyBarItemClickListener);
        historyAdapter.setItemDeleteListener(historyBarItemDeleteListener);

        GridLayoutManager gmHistory = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewHistory.setLayoutManager(gmHistory);
        recyclerViewHistory.addItemDecoration(itemDecorationHor);
        recyclerViewHistory.addItemDecoration(itemDecorationVer);
        recyclerViewHistory.setAdapter(historyAdapter);
    }

    /**
     * 初始化数据，到时可自定义绑定
     * */
    private void initData(){
        //我的搜索推荐
        recommend1ContentList.add("陈翔六点半");
        recommend1ContentList.add("毕加索画");
        recommend1ContentList.add("姐弟恋结婚");

        //热门搜索推荐
        recommend2ContentList.add("柯基摔倒");
        recommend2ContentList.add("银行行长");
        recommend2ContentList.add("退休生活");
        recommend2ContentList.add("中国核武");
        recommend2ContentList.add("坐88路车回家");
        recommend2ContentList.add("解放军军演");
        recommend2ContentList.add("银行放贷");
        recommend2ContentList.add("阿拉斯加训练");
        recommend2ContentList.add("特朗普推特");
        recommend2ContentList.add("王者荣耀老夫子");
        recommend2ContentList.add("吴卓羲督察");
        recommend2ContentList.add("泰国榴莲");
        recommend2ContentList.add("王者荣耀");

        //历史搜索推荐
        historyContentList.add("零食");
        historyContentList.add("美团外卖");
        historyContentList.add("极限挑战");
        historyContentList.add("安卓");
        historyContentList.add("php开发");
        historyContentList.add("鹿晗");
        historyContentList.add("奔跑吧兄弟");
        historyContentList.add("深圳房价");
        historyContentList.add("深圳大学");
        historyContentList.add("科技园");
        historyContentList.add("老梁");
        historyContentList.add("五十一度");
        historyContentList.add("回来吧");
        historyContentList.add("黄渤");
        historyContentList.add("最近上映电影");
        historyContentList.add("小说");
        historyContentList.add("酷狗");
    }

    //第一个推荐栏的监听
    private OnItemClickListener recommend1BarItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(), String.format("你点击了-%s", recommend1ContentList.get(position)), Toast.LENGTH_SHORT).show();
            search(recommend1ContentList.get(position));
            historyContentList.add(0,recommend1ContentList.get(position));

            historyAdapter.notifyDataSetChanged();
        }
    };

    //第二个推荐栏的监听
    private OnItemClickListener recommend2BarItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(), String.format("你点击了-%s", recommend2ContentList.get(position)), Toast.LENGTH_SHORT).show();
            search(recommend1ContentList.get(position));
            historyContentList.add(0,recommend2ContentList.get(position));

            historyAdapter.notifyDataSetChanged();
        }
    };

    //历史记录的监听
    private OnItemClickListener historyBarItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(), String.format("你点击了-%s", historyContentList.get(position)), Toast.LENGTH_SHORT).show();
            search(historyContentList.get(position));

        }
    };

    //历史纪录的删除按钮的监听
    private OnItemDeleteListener historyBarItemDeleteListener = new OnItemDeleteListener() {
        @Override
        public void onItemDelete(View view, int position) {
            Toast.makeText(getApplicationContext(), String.format("你删除了-%s", historyContentList.get(position)), Toast.LENGTH_SHORT).show();
            historyContentList.remove(position);

            historyAdapter.setDisplayCount(historyContentList.size());
            historyAdapter.notifyDataSetChanged();
        }
    };

    //历史记录展开转换，将隐藏的历史纪录展开
    private void onHistoryHideToggle(){
        isHidePartialHistory = !isHidePartialHistory;

        if (isHidePartialHistory){
            int displayCount = historyContentList.size() > 4 ? 4 : historyContentList.size();
            historyAdapter.setDisplayCount(displayCount);

            ivHistoryArrow.setRotation(180);
        }else{
            historyAdapter.setDisplayCount(historyContentList.size());
            ivHistoryArrow.setRotation(0);
        }

        historyAdapter.notifyDataSetChanged();
    }

    //历史记录删除按钮，点击显示所有item的删除
    private void onHistoryDeleteToggle(){
        isInHistoryDelete = !isInHistoryDelete;

        if (isInHistoryDelete){
            //显示所有历史记录
            if (isHidePartialHistory){//如果时隐藏的，则显示出来
                onHistoryHideToggle();
            }

            historyAdapter.setShowDelete(true);
            ivHistoryDelete.setVisibility(View.GONE);
            tvHistoryDeleteFinish.setVisibility(View.VISIBLE);//将隐藏的完成按钮显示
        }else{
            historyAdapter.setShowDelete(false);
            ivHistoryDelete.setVisibility(View.VISIBLE);
            tvHistoryDeleteFinish.setVisibility(View.GONE);
        }

        //在删除历史不能点击历史记录
        ivHistoryArrow.setVisibility(isInHistoryDelete ? View.GONE : View.VISIBLE);
        tvHistory.setClickable(!isInHistoryDelete);

        historyAdapter.notifyDataSetChanged();
    }

    //第二个推荐按钮的转换
    private void onRecommendBarToggle(){
        isShowRecommend = !isShowRecommend;

        llRecommend1Bar.setVisibility(isShowRecommend ? View.VISIBLE : View.GONE);
        llRecommend2Bar.setVisibility(isShowRecommend ? View.VISIBLE : View.GONE);
        llShowRecommendBar.setVisibility(!isShowRecommend ? View.VISIBLE : View.GONE);
    }

    //事件监听
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.tv_history:
            case R.id.iv_arrow:
                onHistoryHideToggle();
                break;

            case R.id.iv_delete:
            case R.id.tv_finish:
                onHistoryDeleteToggle();
                break;

            case R.id.iv_recommend_toggle:
            case R.id.linear_show_recommend:
                onRecommendBarToggle();
                break;
        }
    }

    View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                String keyword = searchTv.getText().toString().trim();
                search(keyword);
            }
            return false;
        }
    };

    private void search(String keyword){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com/s?wd=" + keyword));
        startActivity(intent);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
