package com.android602_4.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android602_4.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/6/29.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragementList;
    private Context context;
    private int[] tabTitle = {R.string.tab_title_home,R.string.tab_title_talk,
            R.string.tab_title_discovery,R.string.tab_title_newspaper};
    private int[] imageSource = {R.drawable.home_selector,R.drawable.talk_selector,
            R.drawable.discovery_selector,R.drawable.news_selector};

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragementList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragementList.size();
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragementList = fragments;
    }

    public View getTabVeiw(int position, TabLayout tabLayout) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_custom,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        textView.setText(context.getResources().getString(tabTitle[position]));
        //设置下面文字颜色
        textView.setTextColor(tabLayout.getTabTextColors());
        //设置icon
        imageView.setImageResource(imageSource[position]);
        return view;
    }
}
