package com.news.domain;

public class HistoryVideoBean {
	
	private String username;
	private String video_title;
	private String video_url;
	private String video_bg_img;
	
	public String getVideo_bg_img() {
		return video_bg_img;
	}
	public void setVideo_bg_img(String video_bg_img) {
		this.video_bg_img = video_bg_img;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getVideo_title() {
		return video_title;
	}
	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	
}
