package com.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.R;
import com.example.bean.HomeListItem;

import java.util.List;

/**
 * Created by 1 on 2018/6/30.
 */

public class HomeListAdapter extends BaseAdapter{

    private int layoutId;
    private List<HomeListItem> mList;
    private Context mContext;

    public HomeListAdapter (Context context, int resource,List<HomeListItem> list){
        mContext = context;
        layoutId = resource;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        HomeListItem item = mList.get(position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView =  view.findViewById(R.id.item_img);
            viewHolder.titleTextView = view.findViewById(R.id.item_text);
            viewHolder.fromTextView = view.findViewById(R.id.from_home);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

//        viewHolder.imageView.setImageResource(item.getImgUrl());
        Glide.with(mContext).load(item.getImgUrl()).into(viewHolder.imageView);
        viewHolder.titleTextView.setText(item.getText());
        viewHolder.fromTextView.setText(item.getFrom());
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView fromTextView;
    }

}
