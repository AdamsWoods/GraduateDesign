package com.example.news.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.R;
import com.example.news.bean.NewsContent.NewsItem;
import com.example.news.newsdetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsItemFragment extends Fragment implements NewsFrgContract.View {

    private NewsFrgContract.Presenter mPresenter;
    private NewsItemRecyclerViewAdapter mListAdapter;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;

    private ArrayList<NewsItem> mList = new ArrayList<>(0);

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_TITLE = "title";

    private int mColumnCount = 1;
    private String mTitle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NewsItemFragment newInstance(int columnCount, String title) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
//        NewsFrgPresenter newsFrgPresenter = new NewsFrgPresenter();
//        fragment.setPresenter();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mTitle = getArguments().getString(ARG_TITLE);
        }

        mListAdapter = new NewsItemRecyclerViewAdapter(getContext(), mList, mListener, itemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(NewsFrgContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsitemlist_frg, container, false);
        // Set the adapter
        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mRecyclerView.setAdapter(mListAdapter);
//            mRecyclerView.addOnScrollListener(mScrollListener);
        }

        //set up progress indicator
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srflayout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        //up refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                mPresenter.refreshNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (mPresenter == null){
            mPresenter = new NewsFrgPresenter(this);
        }
        mPresenter.start(mTitle);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

//    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//    };

    OnListItemListener itemListener = new OnListItemListener() {
        @Override
        public void onListItemClick(NewsItem item) {
            showMessage(item.content);
            mPresenter.openNewsDetails(getContext(), item);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void showMessage(String message){
        Snackbar.make(Objects.requireNonNull(getView()), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNewsDetail(String url, String title) {
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra("content", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void onSuccessed(String tag, ArrayList<NewsItem> list) {
        if (null != list){
            mList.addAll(list);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(String tag, Exception e) {
        showMessage(e.getMessage());
    }

    @Override
    public void onFresh(String tag, ArrayList<NewsItem> list) {
        if (null != list){
            mList.addAll(0,list);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
//            mPresenter.de;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(NewsItem item);
    }

    public interface OnListItemListener{
        void onListItemClick(NewsItem item);
    }
}
