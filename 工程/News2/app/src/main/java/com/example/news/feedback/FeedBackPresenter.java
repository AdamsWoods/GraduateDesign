package com.example.news.feedback;

public class FeedBackPresenter implements FeedBackContract.Presenter{

    private FeedBackContract.View mView;

    public FeedBackPresenter(FeedBackContract.View view){
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
