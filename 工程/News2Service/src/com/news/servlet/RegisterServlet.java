package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.news.dao.UserDao;
import com.news.domain.UserBean;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
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
			//获得请求中传来的账号名和密码
			String accountNumber = request.getParameter("AccountNumber").trim();
			String password = request.getParameter("Password").trim();
			
			//验证是否可以插入结果
			int verifyResult = verifyLogin(accountNumber, password);
			System.out.println("是否可插入"+verifyResult);
			
			Map<String, String> params = new HashMap<>();
			Gson gson= new Gson();
			if(verifyResult == 0){
				if(UserDao.registerUser(accountNumber, password)){
					params.put("result", "success");
					params.put("imgurl", UserDao.queryUser(accountNumber).getImgurl());
				}
				else
					params.put("result", "failed");
			} else {
				params.put("result", "existed");
			}
			out.write(gson.toJson(params).toString());
		}
	}
	
	private int verifyLogin(String userName, String password){
		System.out.println("username："+ userName);
		UserBean userbean = UserDao.queryUser(userName);
		System.out.println("username："+ (userbean==(null)));
		if(userbean==null)
			//用户不存在可插入
			return 0;
		else if (userbean.getUsername().equals(userName)){
			//用户已存在不可插入
			System.out.println("不可插入:verifylogin"+1);
			return 1;
		}
		return 1;
	}

}
