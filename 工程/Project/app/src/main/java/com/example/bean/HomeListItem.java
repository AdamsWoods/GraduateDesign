package com.example.bean;

/**
 * Created by 1 on 2018/6/30.
 */

public class HomeListItem  {

    private String text;
    private String imgUrl;
    private String html;
    private String from;

    public HomeListItem(String text, String imgId, String html, String from){
        this.text = text;
        this.imgUrl = imgId;
        this.html = html;
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgId(String imgId) {
        this.imgUrl = imgId;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml(){
        return html;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getFrom() {
        return from;
    }
}
