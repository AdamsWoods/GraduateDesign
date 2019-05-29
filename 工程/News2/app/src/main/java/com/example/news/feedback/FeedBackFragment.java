package com.example.news.feedback;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.news.R;
import com.example.news.config.CONFIG;

public class FeedBackFragment extends Fragment implements FeedBackContract.View {

    private FeedBackPresenter mPresenter;
    private ProgressBar mLoadingProgress;
    private WebView webView;

    public FeedBackFragment(){

    }

    public static FeedBackFragment newInstance(){
        FeedBackFragment feedBackFragment = new FeedBackFragment();
        Bundle args = new Bundle();
        feedBackFragment.setArguments(args);
        return feedBackFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            //get params
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.feedback_frg, container, false);

        FrameLayout frameLayout = root.findViewById(R.id.contentFrame);
        webView = root.findViewById(R.id.feedbackWeb);
        mLoadingProgress = root.findViewById(R.id.progressBar);
        mLoadingProgress.setMax(100);

        loadFeedBackView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (FeedBackPresenter) presenter;
    }

    @Override
    public void loadFeedBackView() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

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
                            mLoadingProgress.setVisibility(View.GONE);
                        else{
                            mLoadingProgress.setVisibility(View.VISIBLE);
                            mLoadingProgress.setProgress(progress);
                        }
                        super.onProgressChanged(view, progress);
                    }
                });
                return true;
            }
        });

        webView.loadUrl(CONFIG.FEEDBACK_URL);
//        frameLayout.addView(webView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((ViewGroup)(webView.getParent())).removeView(webView);
        webView.destroy();
        webView = null;
    }
}
