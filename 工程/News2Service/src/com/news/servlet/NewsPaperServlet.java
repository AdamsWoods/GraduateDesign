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
import com.news.domain.NewsPaperBean;
import com.news.domain.UserBean;

public class NewsPaperServlet extends HttpServlet{
	
	public NewsPaperServlet() {
	  super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		ArrayList<NewsPaperBean> beanList = new ArrayList<>(0);
		try(PrintWriter out = response.getWriter()) {
			String type = request.getParameter("type").trim();
			if(type.equals("newspaper")) {
				beanList.addAll(UserDao.queryNewsPaper());
			}
			Map<String, Object> map = new HashMap();
			map.put("result", beanList);
			Gson gson= new Gson();
			out.write(gson.toJson(map).toString());
		}
	}

}
