package com.android602_4.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android602_4.NewsPaperDetailsActivity;
import com.android602_4.R;
import com.dreamlive.hotimglibrary.entity.HotArea;
import com.dreamlive.hotimglibrary.utils.FileUtils;
import com.dreamlive.hotimglibrary.view.HotClickView;

import java.io.InputStream;


/**
 * Created by 1 on 2018/6/29.
 */

public class FragmentNewspaper extends BaseFragment implements HotClickView.OnClickListener {

    private HotClickView hotClickView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_newspaper,container,false);
        initParam(view);
        initDatas();
        return view;
    }

    private void initDatas() {
        AssetManager assetManager = getResources().getAssets();
        InputStream imgInputStream = null;
        InputStream fileInputStream = null;
        try {
            imgInputStream = assetManager.open("newspaper.png");
            fileInputStream = assetManager.open("newspaper.xml");
            hotClickView.setImageBitmap(fileInputStream, imgInputStream, HotClickView.FIT_XY);
            hotClickView.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeInputStream(imgInputStream);
            FileUtils.closeInputStream(fileInputStream);
        }
    }

    private void initParam(View view) {
        hotClickView = (HotClickView) view.findViewById(R.id.fragment_newspaper);
    }

    @Override
    public void OnClick(View view, HotArea hotArea) {
        String title = hotArea.getAreaTitle();
        String content = hotArea.getDesc();
        Intent intent = new Intent(getActivity(),NewsPaperDetailsActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        getActivity().startActivityForResult(intent,3);
    }
}
