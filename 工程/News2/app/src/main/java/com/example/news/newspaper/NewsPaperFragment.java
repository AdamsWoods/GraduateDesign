package com.example.news.newspaper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.news.R;

import java.util.ArrayList;
import java.util.List;

public class NewsPaperFragment extends Fragment implements NewsPaperContract.View {

    private RecyclerView recyclerView;
    private RecyclerView newsPaperRV;
    private WebView webView;
    private ProgressBar progressBar;

    private NewsPaperContract.Presenter mPresenter;
    private NewsPaperAdapter mAdapter;
    private NewsPaperAdapter mAdapter_name;
    private ArrayList<String> mList = new ArrayList<>(0);
    private ArrayList<String> papernameList;

    public NewsPaperFragment() {

    }

    public static NewsPaperFragment newInstance(){
        NewsPaperFragment newsPaperFragment = new NewsPaperFragment();
        return newsPaperFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NewsPaperPresenter(getContext(), this);
        //设置监听
        mAdapter = new NewsPaperAdapter(getContext());
        mAdapter.setItemClickListener(itemClickListener);

        mAdapter_name = new NewsPaperAdapter(getContext());
        mAdapter_name.setItemClickListener(newspaperListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.newspaper_frg, container, false);
        recyclerView = root.findViewById(R.id.newspaper_city);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(mAdapter);

        newsPaperRV = root.findViewById(R.id.newspaper_name);
        newsPaperRV.setLayoutManager(new LinearLayoutManager(getContext()));
        newsPaperRV.setAdapter(mAdapter_name);

        webView = root.findViewById(R.id.newspaper);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setMax(100);

        mPresenter.start();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mList = DbHelper.queryCity(getContext());
        if (mList.size() > 0){
            mList.add(0,"热门推荐");
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (NewsPaperContract.Presenter) presenter;
    }

    @Override
    public void onSuccess(List<String> list) {
        mList = (ArrayList<String>) list;
        mList.add(0,"热门推荐");
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
    }

    //item的点击事件
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mList.get(position).equals("热门推荐")){
                papernameList = DbHelper.queryHot(getContext(), "1");
            }else {
                papernameList = DbHelper.queryNewsPaper(getContext(), mList.get(position));
            }
            newsPaperRV.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            mAdapter_name.setData(papernameList);
            mAdapter_name.notifyDataSetChanged();
        }
    };

    private OnItemClickListener newspaperListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            String url = DbHelper.queryUrl(getContext(), papernameList.get(position));
            loadWebView(url);
        }
    };

    private void loadWebView(String url){
        newsPaperRV.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i ==  KeyEvent.KEYCODE_BACK && webView.canGoBack()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.goBack();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                view.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress == 100)
                            progressBar.setVisibility(View.GONE);
                        else{
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setProgress(progress);
                        }
                        super.onProgressChanged(view, progress);
                    }
                });
                return true;
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onBack() {
        if (recyclerView.getVisibility() == View.GONE && newsPaperRV.getVisibility() == View.GONE
                && webView.getVisibility() == View.VISIBLE){
            webView.setVisibility(View.GONE);
            newsPaperRV.setVisibility(View.VISIBLE);
        }else if (recyclerView.getVisibility() == View.GONE && newsPaperRV.getVisibility() == View.VISIBLE
                && webView.getVisibility() == View.GONE){
            recyclerView.setVisibility(View.VISIBLE);
            newsPaperRV.setVisibility(View.GONE);
        }else if (recyclerView.getVisibility() == View.VISIBLE && newsPaperRV.getVisibility() == View.GONE
                && webView.getVisibility() == View.GONE){
            getActivity().finish();
        }
    }
}
