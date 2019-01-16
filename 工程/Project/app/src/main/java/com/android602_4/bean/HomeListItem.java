package com.android602_4.bean;

/**
 * Created by 1 on 2018/6/30.
 */

public class HomeListItem  {

    private String text;
    private int imgId;

    public HomeListItem(String text, int imgId){
        this.text = text;
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
