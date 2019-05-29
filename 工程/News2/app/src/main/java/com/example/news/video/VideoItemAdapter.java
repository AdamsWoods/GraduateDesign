package com.example.news.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.bean.TodayContentBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ViewHolder> {

    private List<TodayContentBean> objects = new ArrayList<TodayContentBean>();
    private List<String> videoList = new ArrayList<>();

    private Context context;
    private OnItemClickListener itemClickListener;

    public VideoItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TodayContentBean> objects,List<String> videoList) {
        this.objects = objects;
        this.videoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new VideoItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TodayContentBean bean=objects.get(position);
        holder.videoPlayer.setUp(videoList.get(position),
                JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                bean.getTitle());

        holder.videoPlayer.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.videoPlayer.currentState == JZVideoPlayer.CURRENT_STATE_NORMAL){
                    holder.videoPlayer.startVideo();
                    holder.videoPlayer.onEvent(JZUserAction.ON_CLICK_START_ICON);

                    itemClickListener.onItemClick(bean.getTitle(),
                            videoList.get(position),
                            bean.getVideo_detail_info().getDetail_video_large_image().getUrl());
                    Log.e("video_url", videoList.get(position));
                }else if (holder.videoPlayer.currentState == JZVideoPlayer.CURRENT_STATE_PLAYING){
                    holder.videoPlayer.onEvent(JZUserAction.ON_CLICK_PAUSE);
                    JZMediaManager.pause();
                    holder.videoPlayer.onStatePause();
                } else if (holder.videoPlayer.currentState == JZVideoPlayer.CURRENT_STATE_PAUSE){
                    holder.videoPlayer.onEvent(JZUserAction.ON_CLICK_RESUME);
                    JZMediaManager.start();
                    holder.videoPlayer.onStatePlaying();
                } else if (holder.videoPlayer.currentState == JZVideoPlayer.CURRENT_STATE_AUTO_COMPLETE){
                    holder.videoPlayer.onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
                    holder.videoPlayer.startVideo();
                }
            }
        });

        Glide.with(context)
                .load(bean.getVideo_detail_info().getDetail_video_large_image().getUrl())
                .into( holder.videoPlayer.thumbImageView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size() - 1;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder{
        private JZVideoPlayerStandard videoPlayer;

        public ViewHolder(View view) {
            super(view);
            videoPlayer = (JZVideoPlayerStandard) view.findViewById(R.id.video_player);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
