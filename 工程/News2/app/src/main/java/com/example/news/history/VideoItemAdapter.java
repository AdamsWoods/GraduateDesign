package com.example.news.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.bean.HistoryVideoBean;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayerStandard;

public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ViewHolder> {

    private ArrayList<HistoryVideoBean.ResultBean> mList;
    private Context mcontext;

    public VideoItemAdapter (Context context){
        this.mcontext = context;
    }

    public void setData(ArrayList<HistoryVideoBean.ResultBean> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false);
        return new VideoItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.videoPlayer.setUp(mList.get(i).getVideo_url()
                ,JZVideoPlayerStandard.SCREEN_WINDOW_LIST
                ,mList.get(i).getVideo_title());

        Glide.with(mcontext).load(mList.get(i).getVideo_bg_img()).into(viewHolder.videoPlayer.thumbImageView);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private JZVideoPlayerStandard videoPlayer;

        public ViewHolder(View view) {
            super(view);
            videoPlayer = (JZVideoPlayerStandard) view.findViewById(R.id.video_player);
        }
    }

}
