package com.example.news.news.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.news.fragment.NewsItemFragment.OnListFragmentInteractionListener;
import com.example.news.news.fragment.NewsItemFragment.OnListItemListener;

import com.example.news.bean.NewsContent.NewsItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemRecyclerViewAdapter.ViewHolder>{

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private final List<NewsItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final OnListItemListener mItemListener;
    private Context mContext;

    public NewsItemRecyclerViewAdapter(Context context, List<NewsItem> items,
                                       OnListFragmentInteractionListener listener, OnListItemListener itemListener) {
        mValues = items;
        mListener = listener;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newsitem_frg, parent, false);
            return new ViewHolder(view);
//        }
//        else {
////            View view = LayoutInflater.from(parent.getContext())
////                    .inflate(R.layout.newsitemlist_frg, parent, false);
////            return new FootHolder(view);
////        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(mContext).load(mValues.get(position).imgUrl).into(holder.mIgView);
//        holder.mIgView.setText(mValues.get(position).imgUrl);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    mItemListener.onListItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount())
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIgView;
        public final TextView mContentView;
        public NewsItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIgView = (ImageView) view.findViewById(R.id.item_image);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

//    public class FootHolder extends RecyclerView.ViewHolder{
//        public final ProgressBar mprogressbar;
//        public final TextView mTextLoading;
//        public final LinearLayout llEnd;
//
//        public FootHolder(@NonNull View itemView) {
//            super(itemView);
//            mprogressbar = itemView.findViewById(R.id.listpb);
//            mTextLoading = itemView.findViewById(R.id.loadtv);
//            llEnd = itemView.findViewById(R.id.ll_end);
//        }
//    }
//
//    public void setLoadState(int loadState){
//        this.s;
//    }
}
