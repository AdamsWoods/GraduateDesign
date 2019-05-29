package com.news.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * 数据库管理类，提供数据库连接和拆解链接功能
 * @author FutureApe
 *
 */
public class DBManager extends HttpServlet {
	
	ServletConfig config; 
	private static String username;
	private static String password;
	private static String url;
	private static Connection connection;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		this.config = config;	//获取数据库配置信息
		username = config.getInitParameter("DBUsername");
		password = config.getInitParameter("DBPasseword");
		url = config.getInitParameter("ConnectionURL");
	}
	
	/**
	 * 获取数据库链接对象
	 */
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (InstantiationException | IllegalAccessException 
				| ClassNotFoundException | SQLException e) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void close(Connection connection, Statement statement){
		try{
			if(statement != null)
				statement.close();
			if(connection != null)
				connection.close();
		} catch (SQLException ex){
			Logger.getLogger(DBManager.class.getName(), null).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void closeAll(Connection connection, Statement statement,
			ResultSet resultSet){
		try{
			if(resultSet != null)
				resultSet.close();
			if(statement != null)
				statement.close();
			if(connection != null)
				connection.close();
		} catch (SQLException ex){
			Logger.getLogger(DBManager.class.getName(), null).log(Level.SEVERE, null, ex);
		}
	}
}






