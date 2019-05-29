package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.news.dao.UserDao;
import com.news.domain.HistoryBean;
import com.news.domain.HistoryVideoBean;
import com.news.domain.UserBean;

public class HistoryQueryServlet extends HttpServlet{
	
	public HistoryQueryServlet() {
	  super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try(PrintWriter out = response.getWriter()) {
			//获得请求中传来的账号名和记录
			String username = request.getParameter("AccountNumber").trim();
			String type = request.getParameter("type").trim();
			
			Gson gson= new Gson();
			
			if(type.equals("history")){
				HistoryBean bean = new HistoryBean();
				UserBean status;
				status = UserDao.queryUser(username);
				if(status.getUsername().equals(username))
					bean = UserDao.queryHistory(username);
				
				out.write(gson.toJson(bean).toString());
			} else if(type.equals("history_video")){
				HistoryVideoBean bean = new HistoryVideoBean();
				ArrayList<HistoryVideoBean> result = UserDao.querHistoryVideo(username);
				Map<String, Object> map = new HashMap();
				map.put("result", result);
				out.write(gson.toJson(map).toString());
			}
			
		}
	}

}
