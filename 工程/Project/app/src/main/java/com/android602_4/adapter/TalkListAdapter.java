package com.android602_4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android602_4.R;
import com.android602_4.bean.TalkListItem;

import java.util.List;

/**
 * Created by 1 on 2018/6/30.
 */

public class TalkListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TalkListItem> mList;
    private int resrouces;

    public TalkListAdapter(Context context, int resrouces, List<TalkListItem> list) {
        mContext = context;
        this.resrouces = resrouces;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TalkListAdapter.ViewHolder viewHolder;
        TalkListItem item = mList.get(position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resrouces, parent, false);
            viewHolder = new TalkListAdapter.ViewHolder();
            viewHolder.itemTitleImg = (ImageView) view.findViewById(R.id.item_title_img);
            viewHolder.itemTitleId = (TextView) view.findViewById(R.id.item_title_id);
            viewHolder.itemTitleTime = (TextView) view.findViewById(R.id.item_title_time);
            viewHolder.itemTitle = (TextView) view.findViewById(R.id.item_title);
            viewHolder.itemImg = (ImageView) view.findViewById(R.id.item_img);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (TalkListAdapter.ViewHolder) view.getTag();
        }

        viewHolder.itemTitleImg.setImageResource(item.getItemTitleImg());
        viewHolder.itemTitleId.setText(item.getItemTielId());
        viewHolder.itemTitleTime.setText(item.getItemTitleTime());
        viewHolder.itemTitle.setText(item.getItemTitle());
        viewHolder.itemImg.setBackgroundResource(item.getItemImg());

        return view;
    }

    class ViewHolder {
        ImageView itemImg;
        TextView itemTitleId;
        ImageView itemTitleImg;
        TextView itemTitleTime;
        TextView itemTitle;
    }
}
