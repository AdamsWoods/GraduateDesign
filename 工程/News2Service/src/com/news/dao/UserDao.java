package com.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.news.db.DBManager;
import com.news.domain.HistoryBean;
import com.news.domain.HistoryBean.Record;
import com.news.domain.HistoryVideoBean;
import com.news.domain.NewsPaperBean;
import com.news.domain.UserBean;

public class UserDao {
	
	/**
	 * 查询指定用户名的详细信息
	 * @param userName
	 * @return
	 */
	public static UserBean queryUser(String userName) {
		Connection connection = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		//生产sql代码
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("SELECT * FROM user WHERE user_name=?");
		
		try{
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, userName);
			
			resultSet = preparedStatement.executeQuery();
			UserBean user = new UserBean();
			if(resultSet.next()) {
				user.setUsername(resultSet.getString("user_name"));
				user.setPassword(resultSet.getString("user_pwd"));
				user.setImgurl(resultSet.getString("user_img"));
				System.out.println(resultSet.getString("user_img"));
				return user;
			}
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DBManager.closeAll(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	public static boolean registerUser(String userName, String password){
		Connection connection = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		
		StringBuilder sqlStatement = new StringBuilder();
		StringBuilder imgUrl = new StringBuilder();
		sqlStatement.append("INSERT INTO user (user_name, user_pwd, user_img) VALUES(?, ?, ?)");
		imgUrl.append("http://24tz764373.qicp.vip/News2Service/image/");
		int i = (int)(Math.random()*10+1);
		imgUrl.append(i%5+".jpg");
		
		try{
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, imgUrl.toString());
			System.out.println("查询语句："+preparedStatement.toString());
			
			resultSet = preparedStatement.executeUpdate();
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
			DBManager.close(connection, preparedStatement);
		}
		return resultSet > 0;
	}
	
	public static boolean addHistory(String username, String title, String imgurl, String details){
		Connection connection = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		int result = 0;
		
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("INSERT INTO history(user_name, title, imgurl, details)VALUES(?, ?, ?, ?)");
		try{
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, imgurl);
			preparedStatement.setString(4, details);
			
			result = preparedStatement.executeUpdate();
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
			DBManager.close(connection, preparedStatement);
		}
		
		return result > 0;
	}
	
	
	//查询历史记录
	public static HistoryBean queryHistory(String username){
		Connection connection = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Record> recordList = new ArrayList(0);
		
		//生产sql代码
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("SELECT * FROM history WHERE user_name=?");
		
		try{
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			HistoryBean history = new HistoryBean();
			while(resultSet.next()) {
				history.setUserName(resultSet.getString("user_name"));
				Record record = history.new Record();
				record.setTitle(resultSet.getString("title"));
				record.setImgurl(resultSet.getString("imgurl"));
				record.setDetails(resultSet.getString("details"));
				recordList.add(record);
//				System.out.println("查询history" + resultSet.getString("url"));
			}
			history.setRecord(recordList);
			return history;
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DBManager.closeAll(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	//查询报纸
	public static ArrayList<NewsPaperBean> queryNewsPaper(){
		Connection connection = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<NewsPaperBean> newspaperList = new ArrayList(0);
		
		//生产sql代码
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("SELECT * FROM newspaper WHERE city <> '' AND newspaper <> '' AND newspaper_url <> '' AND hot is not null ");
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				NewsPaperBean bean = new NewsPaperBean();
				bean.setCity(resultSet.getString("city"));
				bean.setNewspaper(resultSet.getString("newspaper"));
				bean.setNewspaper_url(resultSet.getString("newspaper_url"));
				bean.setHot(resultSet.getString("hot"));
				newspaperList.add(bean);
			}
			System.out.println("query newspaper" + newspaperList.size());
			return newspaperList;
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DBManager.closeAll(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	//插入观看视频历史记录
	public static boolean addHistoryVideo(HistoryVideoBean bean){
		Connection connection = DBManager.getConnection();//获得连接
		PreparedStatement preparedStatement = null;
		int result = 0;
		
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("INSERT INTO history_video(user_name, video_title, video_url, video_bg_img)VALUES(?, ?, ? ,?)");
		try{
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, bean.getUsername());
			preparedStatement.setString(2, bean.getVideo_title());
			preparedStatement.setString(3, bean.getVideo_url());
			preparedStatement.setString(4, bean.getVideo_bg_img());
			
			result = preparedStatement.executeUpdate();
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
			DBManager.close(connection, preparedStatement);
		}
		
		return result > 0;
	}
	
	public static ArrayList<HistoryVideoBean> querHistoryVideo(String username){
		Connection connection = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<HistoryVideoBean> histories = new ArrayList(0);
		
		//生产sql代码
		StringBuilder sqlStatement = new StringBuilder();
		sqlStatement.append("SELECT * FROM history_video WHERE user_name = ?");
		
		try {
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				HistoryVideoBean bean = new HistoryVideoBean();
				bean.setVideo_title(resultSet.getString("video_title"));
				bean.setVideo_url(resultSet.getString("video_url"));
				bean.setVideo_bg_img(resultSet.getString("video_bg_img"));
				histories.add(bean);
			}
			System.out.println("query history_video records:" + histories.size());
			return histories;
		} catch (SQLException ex){
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DBManager.closeAll(connection, preparedStatement, resultSet);
		}
		return null;
	}

}
