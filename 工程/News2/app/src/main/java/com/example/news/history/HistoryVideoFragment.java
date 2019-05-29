package com.example.news.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.R;
import com.example.news.bean.HistoryVideoBean;

import java.util.ArrayList;

public class HistoryVideoFragment extends Fragment implements HistoryContract.VideoView {

    private RecyclerView rvVideo;
    private SwipeRefreshLayout srlVideo;
    private VideoItemAdapter itemAdapter;
    private HistoryVideoPresenter mPresenter;
    private ArrayList<HistoryVideoBean.ResultBean> mList = new ArrayList<>(0);

    public HistoryVideoFragment (){

    }

    public static HistoryVideoFragment newInstence(){
        HistoryVideoFragment fragment = new HistoryVideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemAdapter = new VideoItemAdapter(getContext());
        itemAdapter.setData(mList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_frg2, container, false);

        rvVideo = root.findViewById(R.id.rv_hisotyrvideo);
        srlVideo = root.findViewById(R.id.srl_historyvideo);
        srlVideo.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        srlVideo.setOnRefreshListener(refreshListener);

        rvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVideo.setAdapter(itemAdapter);

        mPresenter = new HistoryVideoPresenter(getContext(), this);
        mPresenter.start();
        return root;
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.loadHistory();
            hideDialog();
        }
    };

    @Override
    public void showVideo() {

    }

    @Override
    public void hideDialog() {
        srlVideo.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srlVideo.setRefreshing(true);
    }

    @Override
    public void onSuccess(ArrayList<HistoryVideoBean.ResultBean> list) {
        mList = list;
        itemAdapter.setData(mList);
        itemAdapter.notifyDataSetChanged();
        hideDialog();
    }

    @Override
    public void setPresenter(Object presenter) {
        this.mPresenter = (HistoryVideoPresenter) presenter;
    }
}
