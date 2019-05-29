package com.news.domain;

import java.util.ArrayList;

public class HistoryBean {
	
	private String username;
	private ArrayList<Record> record;
	
	public void setUserName(String username){
		this.username = username;
	}
	
	public String getUserName(){
		return username;
	}
	
	public void setRecord(ArrayList<Record> record){
		this.record = record;
	}
	
	public ArrayList<Record> getRecord(){
		return record;
	}
	
	
	public class Record {
		private String title;
		private String imgurl;
		private String details;
		
		public Record (){
			
		}
		
		public void setTitle(String title){
			this.title = title;
		}
		
		public String getTitle(){
			return title;
		}
		
		public void setImgurl(String imgurl){
			this.imgurl = imgurl;
		}
		
		public String getImgurl(){
			return imgurl;
		}
		
		public void setDetails(String details){
			this.details = details;
		}
		
		public String getDetails(){
			return details;
		}
	}

}
