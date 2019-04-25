package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.R;
import com.example.TalkDetailsActivity;
import com.example.adapter.TalkListAdapter;
import com.example.bean.TalkListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/6/29.
 */

public class FragmentTalk extends BaseFragment {

    private List<TalkListItem> mList = new ArrayList<>();
    private int flag = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_talk, container, false);
        ListView listView =  view.findViewById(R.id.talk_list);
        //获取数据
        getData();
        //初始化talkListAdapter
        TalkListAdapter talkListAdapter = new TalkListAdapter(getActivity(), R.layout.list_item_talk, mList);
        //绑定数据源
        listView.setAdapter(talkListAdapter);
        //适配器刷新
        talkListAdapter.notifyDataSetChanged();
        //设置点击监听
        listView.setOnItemClickListener(onItemClickListener);
        return view;
    }

    //点击监听
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), TalkDetailsActivity.class);
            getActivity().startActivityForResult(intent,1);
        }
    };

    //获取数据
    public void getData() {
        //手写假数据
        if (flag == 0){
            for (int i = 0; i < 20; i++){
                TalkListItem item = new TalkListItem(R.mipmap.list_talk_id,
                       "item"+i,
                        "6小时前",
                        "成都暴雨万名美团小哥坚持送餐，网友：我敬他是条汉子！",
                        R.mipmap.list_item_talk);
                mList.add(item);
            }
            flag = 1;
            return ;
        }
       return ;
    }
}
