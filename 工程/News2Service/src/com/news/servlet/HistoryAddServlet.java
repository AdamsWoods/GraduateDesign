package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.news.dao.UserDao;
import com.news.domain.HistoryVideoBean;
import com.news.domain.UserBean;

public class HistoryAddServlet extends HttpServlet{
	
	public HistoryAddServlet() {
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
			String accountNumber = request.getParameter("AccountNumber").trim();
			String type = request.getParameter("type").trim();
			
			if(type.equals("history")){
				String title = request.getParameter("Title").trim();
				String imgUrl = request.getParameter("Imgurl").trim();
				String details = request.getParameter("Details").trim();
				
				//验证是否可以插入结果
				int verifyResult = verifyLogin(accountNumber, title, imgUrl, details);
				System.out.println("是否可插入"+verifyResult);
				
				Map<String, String> params = new HashMap<>();
				Gson gson= new Gson();
				if(verifyResult == 0){
						params.put("result", "insert success");
				} else {
						params.put("result", "insert failed");
				} 
				out.write(gson.toJson(params).toString());
			} else if (type.equals("history_video")){
				HistoryVideoBean bean = new HistoryVideoBean();
				bean.setUsername(accountNumber);
				bean.setVideo_title(request.getParameter("title"));
				bean.setVideo_url(request.getParameter("url"));
				bean.setVideo_bg_img(request.getParameter("img"));
				boolean result = UserDao.addHistoryVideo(bean);
				Map<String, String> params = new HashMap<>();
				if(result){
					params.put("result", "insert video history success.");
				} else {
					params.put("result", "insert video history faild.");
				}
				Gson gson = new Gson();
				out.write(gson.toJson(params).toString());
			}
		}
	}

	private int verifyLogin(String username, String title, String imgurl, String details){
		boolean result = UserDao.addHistory(username, title, imgurl, details);
		if(result)
			//用户不存在可插入
			return 0;
		else {
			//用户已存在不可插入
			System.out.println("不可插入:verifylogin"+1);
			return 1;
		}
	}

}
