package com.example.news.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.R;
import com.example.news.bean.NewsContent;
import com.example.news.news.fragment.NewsItemFragment;
import com.example.news.news.fragment.NewsItemRecyclerViewAdapter;
import com.example.news.newsdetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryContract.View {

    private HistoryContract.HistoryPresenter mPresenter;
    private NewsItemRecyclerViewAdapter mListAdapter;
    private NewsItemFragment.OnListFragmentInteractionListener mListener;
    private ArrayList<NewsContent.NewsItem> mList = new ArrayList<>(0);
    private RecyclerView mRecyclerView;

    private String mTitle;

    public HistoryFragment (){
    }

    public static HistoryFragment newInstence(String title){
        HistoryFragment historyFragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        historyFragment.setArguments(args);
        return historyFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mTitle = getArguments().getString("title");
        }
        mListAdapter = new NewsItemRecyclerViewAdapter(getContext(), mList, mListener, itemListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.history_frg, container, false);
        final Context context = getContext();

        mPresenter = new HistoryPresenter(this, context);

        mRecyclerView = view.findViewById(R.id.history_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mListAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.history_sv);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadHistory(context);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (HistoryContract.HistoryPresenter) presenter;
    }

    @Override
    public void onSuccess(ArrayList<NewsContent.NewsItem> list) {
        if (null != list) {
            Log.e("history", list.size()+ "");
//            mList.(list);
            mList.clear();
            mList.addAll(list);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void openNews(NewsContent.NewsItem item) {
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra("content", item.details);
        intent.putExtra("title", item.content);
        startActivity(intent);
    }

    //item监听
    NewsItemFragment.OnListItemListener itemListener = new NewsItemFragment.OnListItemListener() {
        @Override
        public void onListItemClick(NewsContent.NewsItem item) {
            showMessage(item.content);
            mPresenter.openNewsDetails(getContext(), item);
        }
    };

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
