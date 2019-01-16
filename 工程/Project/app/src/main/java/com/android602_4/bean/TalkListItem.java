package com.android602_4.bean;

/**
 * Created by 1 on 2018/6/30.
 */

public class TalkListItem {

    private int itemTitleImg;
    private String itemTielId;
    private String itemTitleTime;
    private String itemTitle;
    private int itemImg;

    public TalkListItem(int itemTitleImg,String itemTielId, String itemTitleTime, String itemTitle,int itemImg){
        this.itemTitleImg = itemTitleImg;
        this.itemTielId = itemTielId;
        this.itemTitleTime = itemTitleTime;
        this.itemTitle = itemTitle;
        this.itemImg = itemImg;
    }

    public int getItemTitleImg() {
        return itemTitleImg;
    }

    public void setItemTitleImg(int itemTitleImg) {
        this.itemTitleImg = itemTitleImg;
    }

    public String getItemTielId() {
        return itemTielId;
    }

    public void setItemTielId(String itemTielId) {
        this.itemTielId = itemTielId;
    }

    public String getItemTitleTime() {
        return itemTitleTime;
    }

    public void setItemTitleTime(String itemTitleTime) {
        this.itemTitleTime = itemTitleTime;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemImg() {
        return itemImg;
    }

    public void setItemImg(int itemImg) {
        this.itemImg = itemImg;
    }
}
