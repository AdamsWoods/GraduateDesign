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
 * 测试登陆servlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String imgurl;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置响应内容类型
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try(PrintWriter out = response.getWriter()) {
			//获得请求中传来的账号名和密码
			String accountNumber = request.getParameter("AccountNumber").trim();
			String password = request.getParameter("Password").trim();
			
			//密码验证结果
			int verifyResult = verifyLogin(accountNumber, password);
			
			Map<String, String> params = new HashMap<>();
			Gson gson= new Gson();
			if(verifyResult == 0){
				params.put("result", "success");
				
				params.put("imgurl", imgurl);
				System.out.println(imgurl);
			} else if(verifyResult == 1){
				params.put("result", "failed");
			} else if(verifyResult == 2)
				params.put("result", "not found");
			out.write(gson.toJson(params).toString());
		}
	}
	
	private int verifyLogin(String userName, String password){
		UserBean user = UserDao.queryUser(userName);
		
		if(user== null)
			return 2;
		else if(!password.equals(user.getPassword()))
			return 1;
		else {
			imgurl = user.getImgurl();
			System.out.println(imgurl);
			return 0;
		}
	}

}
