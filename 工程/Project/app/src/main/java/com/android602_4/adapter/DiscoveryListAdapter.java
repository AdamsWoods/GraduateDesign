package com.android602_4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android602_4.OnItemClickListener;
import com.android602_4.R;
import com.android602_4.bean.DiscoveryItem;

import java.util.List;

/**
 * Created by 1 on 2018/6/30.
 */

public class DiscoveryListAdapter extends RecyclerView.Adapter<DiscoveryListAdapter.ViewHolder>{

    private Context mContext;
    private List<DiscoveryItem> mList;
    private int resources;
    private OnItemClickListener mClickListener;

    public DiscoveryListAdapter(Context context,int resources,List<DiscoveryItem> list){
        mContext = context;
        this.resources = resources;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(resources,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null)
                    mClickListener.onItemClick(v,viewHolder.getAdapterPosition());
            }
        });
//        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = viewHolder.getAdapterPosition();
//                DiscoveryItem item = mList.get(position);
//                Toast.makeText(mContext,"点击了图片"+item.getTitle(),Toast.LENGTH_SHORT).show();
//            }
//        });
        return viewHolder;
    }



    //绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiscoveryItem item = mList.get(position);
        holder.imageView.setBackgroundResource(item.getImg());
        holder.textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private View view;
        private ImageView imageView;
        private TextView textView;
        private OnItemClickListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getAdapterPosition());
        }
    }
}
