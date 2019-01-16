package com.android602_4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android602_4.DiscoveryDetailsActivity;
import com.android602_4.OnItemClickListener;
import com.android602_4.R;
import com.android602_4.TalkDetailsActivity;
import com.android602_4.TitleBuilder;
import com.android602_4.adapter.DiscoveryListAdapter;
import com.android602_4.bean.DiscoveryItem;
import com.android602_4.bean.HomeListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/6/29.
 */

public class FragmentDiscovery extends BaseFragment {

    private int flag = 0;
    private List<DiscoveryItem> listItems = new ArrayList<>();
    private String content = "回忆一下你去过的旅游区厕所，里面耗品是否定时补充？卫生能不能实时保洁？破烂有没有及时维修？\n" +
            "如果答案是否定的，那么这些现象正是视察组所指出的问题：管理水平不高、服务质量不优、人文关怀不够。\n" +
            "管理者往往担心：“有些人一次取走很多免费厕纸，抬高了厕所运行成本。”\n" +
            "怎么办？\n" +
            "委员们建议，推行建立旅游厕所管理责任制和厕所管理服务市场化。\n" +
            "一方面，进一步完善旅游区厕所管理、服务和使用规范标准，借鉴“河长制”“湖长制”经验，探索建立旅游厕所“所长制”，将旅游区厕所建设、管理达标与否作为旅游景区、风景名胜区级别评定的重要依据。\n" +
            "另一方面，坚持政府补贴和市场运作相结合，将旅游厕所管理维护委托第三方，培植厕所经营管理公司新业态。\"";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fregment_discovery,container,false);
        //获取数据
        getData();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.discovery_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //生产适配器
        DiscoveryListAdapter listAdapter = new DiscoveryListAdapter(getActivity(),R.layout.list_item_discovery,listItems);
        //绑定数据源
        recyclerView.setAdapter(listAdapter);
//        //点击监听
        listAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),DiscoveryDetailsActivity.class);
                String title = listItems.get(position).getTitle();
                int imgId = listItems.get(position).getImg();
                intent.putExtra("title",title);
                intent.putExtra("image",imgId);
                intent.putExtra("content",content);
                getActivity().startActivityForResult(intent,2);
            }
        });
        return v;
    }

    public void getData() {
        if (flag == 0){
            for (int i = 0; i < 20; i++) {
                //手写假的数据
                DiscoveryItem item = new DiscoveryItem(getResources().getString(R.string.list_discovery_item_title),
                        R.mipmap.list_item_discovery);
                listItems.add(item);
            }
            flag = 1;
            return;
        }
    }
}
