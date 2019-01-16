package com.android602_4.bean;

/**
 * Created by 1 on 2018/6/30.
 */

public class DiscoveryItem {

    private String title;
    private int img;

    public DiscoveryItem(String title,int img){
        this.title = title;
        this.img = img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
