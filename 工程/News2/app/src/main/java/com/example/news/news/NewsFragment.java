package com.example.news.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.R;
import com.example.news.bean.WeatherBean;
import com.example.news.news.fragment.NewsFrgPresenter;
import com.example.news.news.fragment.NewsItemFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class NewsFragment extends Fragment implements NewsContract.View{

    private NewsContract.Presenter mPresenter;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
//    private String[] mTitles = {"头条","社会"};
    private OnListFragmentInteractionListener fragmentInteractionListener;

    public NewsFragment(){

    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();//加载数据
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.news_act, container, false);

        for (String title : mTitles){
            NewsItemFragment newsFragment = NewsItemFragment.newInstance(1, title);
            mFragments.add(newsFragment);
            new NewsFrgPresenter(newsFragment);
        }

        mViewPager = root.findViewById(R.id.newsVP);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),mFragments,mTitles));

        mTabLayout = root.findViewById(R.id.newsSTL);
        mTabLayout.setViewPager(mViewPager,mTitles, Objects.requireNonNull(getActivity()),mFragments);
        mTabLayout.setCurrentTab(0);

        fragmentInteractionListener = (OnListFragmentInteractionListener) getContext();
        return root;
    }
//
    private void showMessage(String message){
        Snackbar.make(Objects.requireNonNull(getView()), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void loadWeatherSuccess(WeatherBean bean) {
        fragmentInteractionListener.onListFragmentInteraction(bean);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(WeatherBean weather);
    }

}
