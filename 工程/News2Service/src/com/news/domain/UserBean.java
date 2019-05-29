package com.news.domain;

import java.io.Serializable;

public class UserBean implements Serializable{
	
	private int id;
	private String username;
	private String password;
	private String imgurl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setImgurl(String imgurl){
		this.imgurl = imgurl;
	}
	
	public String getImgurl(){
		return imgurl;
	}
	

}